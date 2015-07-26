
package com.wakacommerce.openadmin.web.filter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.common.currency.domain.BroadleafRequestedCurrencyDto;
import com.wakacommerce.common.exception.SiteNotFoundException;
import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.domain.SandBoxType;
import com.wakacommerce.common.sandbox.service.SandBoxService;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.service.SiteService;
import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.util.DeployBehaviorUtil;
import com.wakacommerce.common.web.AbstractBroadleafWebRequestProcessor;
import com.wakacommerce.common.web.BroadleafCurrencyResolver;
import com.wakacommerce.common.web.BroadleafLocaleResolver;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.common.web.BroadleafSandBoxResolver;
import com.wakacommerce.common.web.BroadleafSiteResolver;
import com.wakacommerce.common.web.BroadleafTimeZoneResolver;
import com.wakacommerce.common.web.DeployBehavior;
import com.wakacommerce.common.web.ValidateProductionChangesState;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.remote.SecurityVerifier;
import com.wakacommerce.openadmin.server.security.service.AdminSecurityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;


/**
 * 
 *Phillip Verheyden
 * @see {@link com.wakacommerce.common.web.BroadleafRequestFilter}
 */
@Component("blAdminRequestProcessor")
public class BroadleafAdminRequestProcessor extends AbstractBroadleafWebRequestProcessor {

    public static final String SANDBOX_REQ_PARAM = "blSandBoxId";
    public static final String PROFILE_REQ_PARAM = "blProfileId";
    public static final String CATALOG_REQ_PARAM = "blCatalogId";

    private static final String ADMIN_STRICT_VALIDATE_PRODUCTION_CHANGES_KEY = "admin.strict.validate.production.changes";

    protected final Log LOG = LogFactory.getLog(getClass());

    @Resource(name = "blSiteResolver")
    protected BroadleafSiteResolver siteResolver;

    @Resource(name = "messageSource")
    protected MessageSource messageSource;
    
    @Resource(name = "blLocaleResolver")
    protected BroadleafLocaleResolver localeResolver;
    
    @Resource(name = "blAdminTimeZoneResolver")
    protected BroadleafTimeZoneResolver broadleafTimeZoneResolver;

    @Resource(name = "blCurrencyResolver")
    protected BroadleafCurrencyResolver currencyResolver;

    @Resource(name = "blSandBoxService")
    protected SandBoxService sandBoxService;

    @Resource(name = "blSiteService")
    protected SiteService siteService;

    @Resource(name = "blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService adminSecurityService;

    @Resource(name = "blDeployBehaviorUtil")
    protected DeployBehaviorUtil deployBehaviorUtil;
    
    @Value("${" + ADMIN_STRICT_VALIDATE_PRODUCTION_CHANGES_KEY + ":true}")
    protected boolean adminStrictValidateProductionChanges = true;
    
    @Resource(name="blEntityExtensionManagers")
    protected Map<String, ExtensionManager<?>> entityExtensionManagers;

    @Resource(name = "blAdminRequestProcessorExtensionManager")
    protected AdminRequestProcessorExtensionManager extensionManager;

    @Override
    public void process(WebRequest request) throws SiteNotFoundException {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc == null) {
            brc = new BroadleafRequestContext();
            BroadleafRequestContext.setBroadleafRequestContext(brc);
        }

        brc.getAdditionalProperties().putAll(entityExtensionManagers);

        if (brc.getSite() == null) {
            Site site = siteResolver.resolveSite(request);
            brc.setSite(site);
        }
        brc.setWebRequest(request);
        brc.setIgnoreSite(brc.getSite() == null);
        brc.setAdmin(true);

        if (adminStrictValidateProductionChanges) {
            brc.setValidateProductionChangesState(ValidateProductionChangesState.ADMIN);
        } else {
            brc.setValidateProductionChangesState(ValidateProductionChangesState.UNDEFINED);
        }
        
        Locale locale = localeResolver.resolveLocale(request);
        brc.setLocale(locale);
        
        brc.setMessageSource(messageSource);
        
        TimeZone timeZone = broadleafTimeZoneResolver.resolveTimeZone(request);
        brc.setTimeZone(timeZone);

        // Note: The currencyResolver will set the currency on the BroadleafRequestContext but 
        // later modules (specifically PriceListRequestProcessor in BLC enterprise) may override based
        // on the desired currency.
        BroadleafRequestedCurrencyDto dto = currencyResolver.resolveCurrency(request);
        if (dto != null) {
            brc.setBroadleafCurrency(dto.getCurrencyToUse());
            brc.setRequestedBroadleafCurrency(dto.getRequestedCurrency());
        }

        prepareSandBox(request, brc);
        prepareProfile(request, brc);
        prepareCatalog(request, brc);
    }

    protected void prepareProfile(WebRequest request, BroadleafRequestContext brc) {
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any profile
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(PROFILE_REQ_PARAM, WebRequest.SCOPE_GLOBAL_SESSION);
            }
        } else {
            Site profile = null;
            if (StringUtils.isNotBlank(request.getParameter(PROFILE_REQ_PARAM))) {
                Long profileId = Long.parseLong(request.getParameter(PROFILE_REQ_PARAM));
                profile = siteService.retrievePersistentSiteById(profileId);
                if (profile == null) {
                    throw new IllegalArgumentException(String.format("Unable to find the requested profile: %s", profileId));
                }
            }

            if (profile == null) {
                Long previouslySetProfileId = null;
                if (BLCRequestUtils.isOKtoUseSession(request)) {
                    previouslySetProfileId = (Long) request.getAttribute(PROFILE_REQ_PARAM,
                        WebRequest.SCOPE_GLOBAL_SESSION);
                }
                if (previouslySetProfileId != null) {
                    profile = siteService.retrievePersistentSiteById(previouslySetProfileId);
                }
            }

            if (profile == null) {
                List<Site> profiles = new ArrayList<Site>();
                if (brc.getNonPersistentSite() != null) {
                    Site currentSite = siteService.retrievePersistentSiteById(brc.getNonPersistentSite().getId());
                    if (extensionManager != null) {
                        ExtensionResultHolder<Set<Site>> profilesResult = new ExtensionResultHolder<Set<Site>>();
                        extensionManager.getProxy().retrieveProfiles(currentSite, profilesResult);
                        if (!CollectionUtils.isEmpty(profilesResult.getResult())) {
                            profiles.addAll(profilesResult.getResult());
                        }
                    }
                }
                if (profiles.size() == 1) {
                    profile = profiles.get(0);
                }
            }

            if (profile != null) {
                if (BLCRequestUtils.isOKtoUseSession(request)) {
                    request.setAttribute(PROFILE_REQ_PARAM, profile.getId(), WebRequest.SCOPE_GLOBAL_SESSION);
                }
                brc.setCurrentProfile(profile);
            }
        }
    }

    protected void prepareCatalog(WebRequest request, BroadleafRequestContext brc) {
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any catalog
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(CATALOG_REQ_PARAM, WebRequest.SCOPE_GLOBAL_SESSION);
            }
        } else {
            Catalog catalog = null;
            if (StringUtils.isNotBlank(request.getParameter(CATALOG_REQ_PARAM))) {
                Long catalogId = Long.parseLong(request.getParameter(CATALOG_REQ_PARAM));
                catalog = siteService.findCatalogById(catalogId);
                if (catalog == null) {
                    throw new IllegalArgumentException(String.format("Unable to find the requested catalog: %s", catalogId));
                }
            }

            if (catalog == null) {
                Long previouslySetCatalogId = null;
                if (BLCRequestUtils.isOKtoUseSession(request)) {
                    previouslySetCatalogId = (Long) request.getAttribute(CATALOG_REQ_PARAM,
                        WebRequest.SCOPE_GLOBAL_SESSION);
                }
                if (previouslySetCatalogId != null) {
                    catalog = siteService.findCatalogById(previouslySetCatalogId);
                }
            }

            if (catalog == null) {
                List<Catalog> catalogs = new ArrayList<Catalog>();
                if (brc.getNonPersistentSite() != null) {
                    Site currentSite = siteService.retrievePersistentSiteById(brc.getNonPersistentSite().getId());
                    if (extensionManager != null) {
                        ExtensionResultHolder<Set<Catalog>> catalogResult = new ExtensionResultHolder<Set<Catalog>>();
                        extensionManager.getProxy().retrieveCatalogs(currentSite, catalogResult);
                        if (!CollectionUtils.isEmpty(catalogResult.getResult())) {
                            catalogs.addAll(catalogResult.getResult());
                        }
                    }
                }
                if (catalogs.size() == 1) {
                    catalog = catalogs.get(0);
                }
            }

            if (catalog != null) {
                if (BLCRequestUtils.isOKtoUseSession(request)) {
                    request.setAttribute(CATALOG_REQ_PARAM, catalog.getId(), WebRequest.SCOPE_GLOBAL_SESSION);
                }
                brc.setCurrentCatalog(catalog);
            }
        }
    }

    protected void prepareSandBox(WebRequest request, BroadleafRequestContext brc) {
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any sandbox
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
        } else {
            SandBox sandBox = null;
            if (StringUtils.isNotBlank(request.getParameter(SANDBOX_REQ_PARAM))) {
                Long sandBoxId = Long.parseLong(request.getParameter(SANDBOX_REQ_PARAM));
                sandBox = sandBoxService.retrieveUserSandBoxForParent(adminUser.getId(), sandBoxId);
                if (sandBox == null) {
                    SandBox approvalOrUserSandBox = sandBoxService.retrieveSandBoxManagementById(sandBoxId);
                    if (approvalOrUserSandBox != null) {
                        if (approvalOrUserSandBox.getSandBoxType().equals(SandBoxType.USER)) {
                            sandBox = approvalOrUserSandBox;
                        } else {
                            sandBox = sandBoxService.createUserSandBox(adminUser.getId(), approvalOrUserSandBox);
                        }
                    }
                }
            }

            if (sandBox == null) {
                Long previouslySetSandBoxId = null;
                if (BLCRequestUtils.isOKtoUseSession(request)) {
                    previouslySetSandBoxId = (Long) request.getAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR,
                        WebRequest.SCOPE_GLOBAL_SESSION);
                }
                if (previouslySetSandBoxId != null) {
                    sandBox = sandBoxService.retrieveSandBoxManagementById(previouslySetSandBoxId);
                }
            }

            if (sandBox == null) {
                List<SandBox> defaultSandBoxes = sandBoxService.retrieveSandBoxesByType(SandBoxType.DEFAULT);
                if (defaultSandBoxes.size() > 1) {
                    throw new IllegalStateException("Only one sandbox should be configured as default");
                }

                SandBox defaultSandBox;
                if (defaultSandBoxes.size() == 1) {
                    defaultSandBox = defaultSandBoxes.get(0);
                } else {
                    defaultSandBox = sandBoxService.createDefaultSandBox();
                }

                sandBox = sandBoxService.retrieveUserSandBoxForParent(adminUser.getId(), defaultSandBox.getId());
                if (sandBox == null) {
                    sandBox = sandBoxService.createUserSandBox(adminUser.getId(), defaultSandBox);
                }
            }

            // If the user just changed sandboxes, we want to update the database record.
            Long previouslySetSandBoxId = null;
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                previouslySetSandBoxId = (Long) request.getAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
            if (previouslySetSandBoxId != null && !sandBox.getId().equals(previouslySetSandBoxId)) {
                adminUser.setLastUsedSandBoxId(sandBox.getId());
                adminUser = adminSecurityService.saveAdminUser(adminUser);
            }

            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.setAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR, sandBox.getId(), WebRequest.SCOPE_GLOBAL_SESSION);
            }
            brc.setSandBox(sandBox);
            brc.setDeployBehavior(deployBehaviorUtil.isProductionSandBoxMode() ? DeployBehavior.CLONE_PARENT : DeployBehavior.OVERWRITE_PARENT);
            brc.getAdditionalProperties().put("adminUser", adminUser);
        }
    }

    @Override
    public void postProcess(WebRequest request) {
        ThreadLocalManager.remove();
    }

}
