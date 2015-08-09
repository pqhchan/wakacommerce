package com.wakacommerce.common.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.RequestDTO;
import com.wakacommerce.common.RequestDTOImpl;
import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.common.currency.domain.BroadleafRequestedCurrencyDto;
import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.Theme;
import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.util.DeployBehaviorUtil;
import com.wakacommerce.common.web.exception.HaltFilterChainException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blRequestProcessor")
public class WakaRequestProcessor extends AbstractWakaWebRequestProcessor {

    protected final Log LOG = LogFactory.getLog(getClass());

    private static String REQUEST_DTO_PARAM_NAME = WakaRequestFilter.REQUEST_DTO_PARAM_NAME;
    public static String REPROCESS_PARAM_NAME = "REPROCESS_BLC_REQUEST";
    
    private static final String SITE_STRICT_VALIDATE_PRODUCTION_CHANGES_KEY = "site.strict.validate.production.changes";

    @Resource(name = "blSiteResolver")
    protected BroadleafSiteResolver siteResolver;

    @Resource(name = "blLocaleResolver")
    protected BroadleafLocaleResolver localeResolver;

    @Resource(name = "blCurrencyResolver")
    protected BroadleafCurrencyResolver currencyResolver;

    @Resource(name = "blSandBoxResolver")
    protected BroadleafSandBoxResolver sandboxResolver;

    @Resource(name = "blThemeResolver")
    protected BroadleafThemeResolver themeResolver;

    @Resource(name = "messageSource")
    protected MessageSource messageSource;

    @Resource(name = "blTimeZoneResolver")
    protected BroadleafTimeZoneResolver broadleafTimeZoneResolver;
    
    @Value("${thymeleaf.threadLocalCleanup.enabled}")
    protected boolean thymeleafThreadLocalCleanupEnabled = true;

    @Value("${" + SITE_STRICT_VALIDATE_PRODUCTION_CHANGES_KEY + ":false}")
    protected boolean siteStrictValidateProductionChanges = false;

    @Resource(name = "blDeployBehaviorUtil")
    protected DeployBehaviorUtil deployBehaviorUtil;
    
    @SuppressWarnings("rawtypes")
	@Resource(name="blEntityExtensionManagers")
    protected Map<String, ExtensionManager> entityExtensionManagers;
    
    @Override
    public void process(WebRequest request) {
        WakaRequestContext brc = new WakaRequestContext();
        brc.getAdditionalProperties().putAll(entityExtensionManagers);
        
        Site site = siteResolver.resolveSite(request);
        
        brc.setNonPersistentSite(site);
        brc.setWebRequest(request);
        if (site == null) {
            brc.setIgnoreSite(true);
        }
        brc.setAdmin(false);

        if (siteStrictValidateProductionChanges) {
            brc.setValidateProductionChangesState(ValidateProductionChangesState.SITE);
        } else {
            brc.setValidateProductionChangesState(ValidateProductionChangesState.UNDEFINED);
        }

        WakaRequestContext.setWakaRequestContext(brc);

        Locale locale = localeResolver.resolveLocale(request);
        brc.setLocale(locale);
        TimeZone timeZone = broadleafTimeZoneResolver.resolveTimeZone(request);
        BroadleafRequestedCurrencyDto currencyDto = currencyResolver.resolveCurrency(request);
        // Assumes BroadleafProcess
        RequestDTO requestDTO = (RequestDTO) request.getAttribute(REQUEST_DTO_PARAM_NAME, WebRequest.SCOPE_REQUEST);
        if (requestDTO == null) {
            requestDTO = new RequestDTOImpl(request);
        }

        SandBox currentSandbox = sandboxResolver.resolveSandBox(request, site);
        
        // When a user elects to switch his sandbox, we want to invalidate the current session. We'll then redirect the 
        // user to the current URL so that the configured filters trigger again appropriately.
        Boolean reprocessRequest = (Boolean) request.getAttribute(WakaRequestProcessor.REPROCESS_PARAM_NAME, WebRequest.SCOPE_REQUEST);
        if (reprocessRequest != null && reprocessRequest) {
            LOG.debug("Reprocessing request");
            if (request instanceof ServletWebRequest) {
                HttpServletRequest hsr = ((ServletWebRequest) request).getRequest();
                
                clearBroadleafSessionAttrs(request);
                
                StringBuffer url = hsr.getRequestURL();
                if (hsr.getQueryString() != null) {
                    url.append('?').append(hsr.getQueryString());
                }
                try {
                    ((ServletWebRequest) request).getResponse().sendRedirect(url.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                throw new HaltFilterChainException("Reprocess required, redirecting user");
            }
        }
        
        
        if (currentSandbox != null) {
            SandBoxContext previewSandBoxContext = new SandBoxContext();
            previewSandBoxContext.setSandBoxId(currentSandbox.getId());
            previewSandBoxContext.setPreviewMode(true);
            SandBoxContext.setSandBoxContext(previewSandBoxContext);
        }
        if (currencyDto != null) {
            brc.setBroadleafCurrency(currencyDto.getCurrencyToUse());
            brc.setRequestedBroadleafCurrency(currencyDto.getRequestedCurrency());
        }

        brc.setSandBox(currentSandbox);
        brc.setDeployBehavior(deployBehaviorUtil.isProductionSandBoxMode() ? DeployBehavior.CLONE_PARENT : DeployBehavior.OVERWRITE_PARENT);

        // Note that this must happen after the request context is set up as resolving a theme is dependent on site
        Theme theme = themeResolver.resolveTheme(request);
        brc.setTheme(theme);

        brc.setMessageSource(messageSource);
        brc.setTimeZone(timeZone);
        brc.setRequestDTO(requestDTO);
        @SuppressWarnings("unchecked")
		Map<String, Object> ruleMap = (Map<String, Object>) request.getAttribute("blRuleMap", WebRequest.SCOPE_REQUEST);
        if (ruleMap == null) {
            LOG.trace("Creating ruleMap and adding in Locale.");
            ruleMap = new HashMap<String, Object>();
            request.setAttribute("blRuleMap", ruleMap, WebRequest.SCOPE_REQUEST);
        } else {
            LOG.trace("Using pre-existing ruleMap - added by non standard BLC process.");
        }
        ruleMap.put("locale", locale);

        String adminUserId = request.getParameter(WakaRequestFilter.ADMIN_USER_ID_PARAM_NAME);
        if (StringUtils.isNotBlank(adminUserId)) {
            //TODO: Add token logic to secure the admin user id
            brc.setAdminUserId(Long.parseLong(adminUserId));
        }

    }

    @Override
    public void postProcess(WebRequest request) {
        ThreadLocalManager.remove();
    }
    
    protected void clearBroadleafSessionAttrs(WebRequest request) {
        if (BLCRequestUtils.isOKtoUseSession(request)) {
            request.removeAttribute(WakaLocaleResolverImpl.LOCALE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(BroadleafCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(BroadleafTimeZoneResolverImpl.TIMEZONE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);

            // From CustomerStateRequestProcessorImpl, using explicit String because it's out of module
            request.removeAttribute("_blc_anonymousCustomer", WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute("_blc_anonymousCustomerId", WebRequest.SCOPE_GLOBAL_SESSION);
        }
    }
}
