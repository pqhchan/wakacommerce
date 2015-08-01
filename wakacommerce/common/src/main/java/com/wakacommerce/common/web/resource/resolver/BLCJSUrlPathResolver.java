  
package com.wakacommerce.common.web.resource.resolver;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.web.WakaRequestContext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@link ResourceResolver} that replaces the //BLC-SERVLET-CONTEXT and //BLC-SITE-BASEURL" 
 * tokens before serving the BLC.js file.
 * 
 * This component modifies the path and works in conjunction with the {@link BLCJSResourceResolver}
 * which loads the modified file.
 * 
 * The processes were split to allow for caching of the resource but not the URL path.
 */
@Component("blBLCJSUrlPathResolver")
public class BLCJSUrlPathResolver extends AbstractResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BLCJSUrlPathResolver.class);

    private static final String BLC_JS_NAME = "BLC.js";

    private int order = BroadleafResourceResolverOrder.BLC_JS_PATH_RESOLVER;

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
            ResourceResolverChain chain) {
        if (resourceUrlPath.contains(BLC_JS_NAME)) {
            Site site = WakaRequestContext.getWakaRequestContext().getNonPersistentSite();
            if (site != null && site.getId() != null) {
                return addVersion(resourceUrlPath, "-"+site.getId());
            } else {
                return resourceUrlPath;
            }                       
        }
        return chain.resolveUrlPath(resourceUrlPath, locations);
    }
    
    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {
        return chain.resolveResource(request, requestPath, locations);
    }

    protected String addVersion(String requestPath, String version) {
        String baseFilename = StringUtils.stripFilenameExtension(requestPath);
        String extension = StringUtils.getFilenameExtension(requestPath);
        return baseFilename + version + "." + extension;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
