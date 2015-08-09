package com.wakacommerce.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.spring4.view.AbstractThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.controller.WakaControllerUtility;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class WakaThymeleafViewResolver extends ThymeleafViewResolver {
    private static final Log LOG = LogFactory.getLog(WakaThymeleafViewResolver.class);
    
    @Resource(name = "blBroadleafThymeleafViewResolverExtensionManager")
    protected WakaThymeleafViewResolverExtensionManager extensionManager;
    
    public static final String EXTENSION_TEMPLATE_ATTR_NAME = "extensionTemplateAttr";

    public static final String AJAX_REDIRECT_URL_PREFIX = "ajaxredirect:";
    
    protected Map<String, String> layoutMap = new HashMap<String, String>();
    protected String fullPageLayout = "layout/fullPageLayout";
    protected String iframeLayout = "layout/iframeLayout";
    
    protected boolean useThymeleafLayoutDialect() {
        return BLCSystemProperty.resolveBooleanSystemProperty("thymeleaf.useLayoutDialect");
    }
    
    protected boolean canHandle(final String viewName) {
        final String[] viewNamesToBeProcessed = getViewNames();
        final String[] viewNamesNotToBeProcessed = getExcludedViewNames();
        return ((viewNamesToBeProcessed == null || PatternMatchUtils.simpleMatch(viewNamesToBeProcessed, viewName)) &&
                (viewNamesNotToBeProcessed == null || !PatternMatchUtils.simpleMatch(viewNamesNotToBeProcessed, viewName)));
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
        extensionManager.getProxy().overrideView(erh, viewName, isAjaxRequest());

        String viewOverride = (String) erh.getResult();

        if (viewOverride != null) {
            viewName = viewOverride;
        }

        return super.resolveViewName(viewName, locale);
    }

    @Override
    protected View createView(final String viewName, final Locale locale) throws Exception {
        if (!canHandle(viewName)) {
            LOG.trace("[THYMELEAF] View {" + viewName + "} cannot be handled by ThymeleafViewResolver. Passing on to the next resolver in the chain");
            return null;
        }
        
        if (viewName.startsWith(AJAX_REDIRECT_URL_PREFIX)) {
            LOG.trace("[THYMELEAF] View {" + viewName + "} is an ajax redirect, and will be handled directly by WakaThymeleafViewResolver");
            String redirectUrl = viewName.substring(AJAX_REDIRECT_URL_PREFIX.length());
            return loadAjaxRedirectView(redirectUrl, locale);
        }
        
        return super.createView(viewName, locale);
    }

    protected View loadAjaxRedirectView(String redirectUrl, final Locale locale) throws Exception {
        if (isAjaxRequest()) {
            String viewName = "utility/blcRedirect";
            addStaticVariable(WakaControllerUtility.BLC_REDIRECT_ATTRIBUTE, redirectUrl);
            return super.loadView(viewName, locale);
        } else {
            return new RedirectView(redirectUrl, false, isRedirectHttp10Compatible());
        }
    }
    
    @Override
    protected View loadView(final String originalViewName, final Locale locale) throws Exception {
        String viewName = originalViewName;
        
        if (!isAjaxRequest() && !useThymeleafLayoutDialect()) {
            String longestPrefix = "";
            
            for (Entry<String, String> entry : layoutMap.entrySet()) {
                String viewPrefix = entry.getKey();
                String viewLayout = entry.getValue();
                
                if (viewPrefix.length() > longestPrefix.length()) {
                    if (originalViewName.startsWith(viewPrefix)) {
                        longestPrefix = viewPrefix;
                        
                        if (!"NONE".equals(viewLayout)) {
                            viewName = viewLayout;
                        }
                    }
                }
            }  
            
            if (longestPrefix.equals("")) {
                viewName = getFullPageLayout();
            }
        }

        AbstractThymeleafView view = null;
        boolean ajaxRequest = isAjaxRequest();
        
        ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
        extensionManager.getProxy().provideTemplateWrapper(erh, originalViewName, ajaxRequest);
        String templateWrapper = erh.getResult();

        if (templateWrapper != null && ajaxRequest) {
            view = (AbstractThymeleafView) super.loadView(templateWrapper, locale);
            view.addStaticVariable("wrappedTemplate", viewName);
        } else {
            view = (AbstractThymeleafView) super.loadView(viewName, locale);
        }
        
        if (!ajaxRequest) {
            view.addStaticVariable("templateName", originalViewName);
        }
        
        return view;
    }
    
    @Override
    protected Object getCacheKey(String viewName, Locale locale) {
        String cacheKey = viewName + "_" + locale + "_" + isAjaxRequest();

        ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
        extensionManager.getProxy().appendCacheKey(erh, viewName, isAjaxRequest());

        String addlCacheKey = (String) erh.getResult();

        if (addlCacheKey != null) {
            cacheKey = cacheKey + "_" + addlCacheKey;
        }

        return cacheKey;
    }
    
    protected boolean isIFrameRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String iFrameParameter = request.getParameter("blcIFrame");
        return (iFrameParameter != null && "true".equals(iFrameParameter));
    }
    
    protected boolean isAjaxRequest() {
        // First, let's try to get it from the WakaRequestContext
        HttpServletRequest request = null;
        if (WakaRequestContext.getWakaRequestContext() != null) {
            HttpServletRequest brcRequest = WakaRequestContext.getWakaRequestContext().getRequest();
            if (brcRequest != null) {
                request = brcRequest;
            }
        }
        
        // If we didn't find it there, we might be outside of a security-configured uri. Let's see if the filter got it
        if (request == null) {
            try {
                request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
            } catch (ClassCastException e) {
                // In portlet environments, we won't be able to cast to a ServletRequestAttributes. We don't want to 
                // blow up in these scenarios.
                LOG.warn("Unable to cast to ServletRequestAttributes and the request in WakaRequestContext " + 
                         "was not set. This may introduce incorrect AJAX behavior.");
            }
        }
        
        // If we still don't have a request object, we'll default to non-ajax
        if (request == null) {
            return false;
        }
                
        return WakaControllerUtility.isAjaxRequest(request);
    }

    public Map<String, String> getLayoutMap() {
        return layoutMap;
    }

    public void setLayoutMap(Map<String, String> layoutMap) {
        this.layoutMap = layoutMap;
    }

    public String getFullPageLayout() {
        return fullPageLayout;
    }

    public void setFullPageLayout(String fullPageLayout) {
        this.fullPageLayout = fullPageLayout;
    }

    public String getIframeLayout() {
        return iframeLayout;
    }

    public void setIframeLayout(String iframeLayout) {
        this.iframeLayout = iframeLayout;
    }
    
}
