package com.wakacommerce.cms.web.file;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.wakacommerce.cms.common.AssetNotFoundException;
import com.wakacommerce.cms.file.service.StaticAssetStorageService;
import com.wakacommerce.cms.file.service.operation.NamedOperationComponent;
import com.wakacommerce.cms.file.service.operation.NamedOperationManager;
import com.wakacommerce.cms.file.service.operation.StaticMapNamedOperationComponent;
import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.BroadleafSiteResolver;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticAssetViewController extends AbstractController {

    private static final Log LOG = LogFactory.getLog(StaticAssetViewController.class);
    
    protected String assetServerUrlPrefix;
    protected String viewResolverName;

    @Resource(name="blStaticAssetStorageService")
    protected StaticAssetStorageService staticAssetStorageService;

    @Resource(name = "blSiteResolver")
    protected BroadleafSiteResolver siteResolver;

    @Resource
    protected NamedOperationManager namedOperationManager;

    @PostConstruct
    protected void init() {
        if (getAllowUnnamedImageManipulation()) {
            LOG.warn("Allowing image manipulation strictly through URL parameters that the application does not know about"
                    + " is not recommended and can be used maliciously for nefarious purposes. Instead, you should set up"
                    + " a map of known operations and the transformations associated with each operation. This behavior will"
                    + " default to false starting with Broadleaf 3.2.0-GA. For more information"
                    + " see the docs at http://www.broadleafcommerce.com/docs/core/current/broadleaf-concepts/additional-configuration/asset-server-configuration");
        }
    }

    protected Map<String, String> convertParameterMap(Map<String, String[]> parameterMap) {
        Map<String, String> convertedMap = new LinkedHashMap<String, String>(parameterMap.size());
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (isAllowedUrlParameter(entry.getKey())) {
                convertedMap.put(entry.getKey(), StringUtils.join(entry.getValue(), ','));
            } else {
                // we didn't find it in the list of named operations, lets see if we allow that to happen
                if (getAllowUnnamedImageManipulation()) {
                    convertedMap.put(entry.getKey(), StringUtils.join(entry.getValue(), ','));
                } else {
                    LOG.debug("Stripping URL image manipulation parameter " + entry.getKey() + " as it is not a known named"
                            + " operation.");
                }
            }
        }

        return convertedMap;
    }
    
    protected boolean isAllowedUrlParameter(String parameter) {
        boolean parameterWithinNamedOperations = false;
        for (NamedOperationComponent component : namedOperationManager.getNamedOperationComponents()) {
            if (component.getClass().isAssignableFrom(StaticMapNamedOperationComponent.class)) {
                parameterWithinNamedOperations = ((StaticMapNamedOperationComponent) component).getNamedOperations().containsKey(parameter);
            }
            if (parameterWithinNamedOperations) {
                break;
            }
        }
        return parameterWithinNamedOperations;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fullUrl = removeAssetPrefix(request.getRequestURI());

        // Static Assets don't typically go through the Spring Security pipeline but they may need access 
        // to the site 
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        context.setNonPersistentSite(siteResolver.resolveSite(new ServletWebRequest(request, response)));
        try {
            Map<String, String> model = staticAssetStorageService.getCacheFileModel(fullUrl, convertParameterMap(request.getParameterMap()));
            return new ModelAndView(viewResolverName, model);
        } catch (AssetNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } catch (Exception e) {
            LOG.error("Unable to retrieve static asset", e);
            throw new RuntimeException(e);
        } finally {
            ThreadLocalManager.remove();
        }
    }
    
    protected String removeAssetPrefix(String requestURI) {
        String fileName = requestURI;
        if (assetServerUrlPrefix != null) {
            int pos = fileName.indexOf(assetServerUrlPrefix);
            fileName = fileName.substring(pos+assetServerUrlPrefix.length());

            if (! fileName.startsWith("/")) {
                fileName = "/"+fileName;
            }
        }

        return fileName;
        
    }
    
    public boolean getAllowUnnamedImageManipulation() {
        boolean allowUnnamedImageManipulation = 
                BLCSystemProperty.resolveBooleanSystemProperty("asset.server.allow.unnamed.image.manipulation");
        return allowUnnamedImageManipulation;
    }

    public String getAssetServerUrlPrefix() {
        return assetServerUrlPrefix;
    }

    public void setAssetServerUrlPrefix(String assetServerUrlPrefix) {        
        this.assetServerUrlPrefix = assetServerUrlPrefix;
    }

    public String getViewResolverName() {
        return viewResolverName;
    }

    public void setViewResolverName(String viewResolverName) {
        this.viewResolverName = viewResolverName;
    }
}
