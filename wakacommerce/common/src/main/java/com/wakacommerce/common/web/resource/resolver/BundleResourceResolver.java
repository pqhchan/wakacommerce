  
package com.wakacommerce.common.web.resource.resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import com.wakacommerce.common.resource.service.ResourceBundlingService;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@code ResourceResolver} that is used to serve previously bundled files.
 * 
 * <p>
 * It works with {@link ResourceBundlingService} which is able to create and read bundle files.
 *  
 */
@Component("blBundleResourceResolver")
public class BundleResourceResolver extends AbstractResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BundleResourceResolver.class);

    private int order = BroadleafResourceResolverOrder.BLC_BUNDLE_RESOURCE_RESOLVER;

    @javax.annotation.Resource(name = "blResourceBundlingService")
    protected ResourceBundlingService bundlingService;

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {

        if (requestPath != null) {
            if (isBundleFile(requestPath)) {
                Resource bundle = bundlingService.resolveBundleResource(requestPath);

                logTraceInformation(bundle);
                if (bundle != null && bundle.exists()) {
                    return bundle;
                }
            }
        }

        return chain.resolveResource(request, requestPath, locations);
    }

    protected void logTraceInformation(Resource bundle) {
        if (LOG.isTraceEnabled()) {
            if (bundle == null) {
                LOG.trace("Resolving bundle, bundle is null");
            } else {
                LOG.trace("Resolving bundle, bundle is not null, bundle.exists() == " + bundle.exists() +
                        " ,filename = " + bundle.getFilename());
                try {
                    LOG.trace("Resolving bundle - File Path" + bundle.getFile().getAbsolutePath());
                } catch (IOException e) {
                    LOG.error("IOException debugging bundle code", e);
                }
            }
        }
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
            ResourceResolverChain chain) {
        if (resourceUrlPath != null) {
            if (isBundleFile(resourceUrlPath)) {
                return resourceUrlPath;
            }
        }

        return chain.resolveUrlPath(resourceUrlPath, locations);

    }

    protected boolean isBundleFile(String requestPath) {
        boolean isBundle = bundlingService.checkForRegisteredBundleFile(requestPath);
        if (logger.isTraceEnabled()) {
            logger.trace("Checking isBundleFile, requestPath=\"" + requestPath + "\" isBundle=\"" + isBundle + "\"");
        }
        return isBundle;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
