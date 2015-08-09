  
package com.wakacommerce.common.web.resource.resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.resource.VersionStrategy;

import com.wakacommerce.common.resource.service.ResourceBundlingService;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blVersionResourceResolver")
public class BroadleafVersionResourceResolver extends VersionResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BroadleafVersionResourceResolver.class);

    private int order = BroadleafResourceResolverOrder.BLC_VERSION_RESOURCE_RESOLVER;

    @Value("${resource.versioning.enabled:true}")
    protected boolean resourceVersioningEnabled;

    @javax.annotation.Resource(name = "blResourceBundlingService")
    protected ResourceBundlingService bundlingService;

    @javax.annotation.Resource(name = "blVersionResourceResolverStrategyMap")
    protected Map<String, VersionStrategy> versionStrategyMap;

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {
        if (resourceVersioningEnabled && !bundlingService.checkForRegisteredBundleFile(requestPath)) {
            return super.resolveResourceInternal(request, requestPath, locations, chain);
        } else {
            return chain.resolveResource(request, requestPath, locations);
        }
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {
        if (resourceVersioningEnabled && !bundlingService.checkForRegisteredBundleFile(resourceUrlPath)) {
            String result = super.resolveUrlPathInternal(resourceUrlPath, locations, chain);

            // Spring's default version handler will return null if it doesn't have a strategy
            // for that resource - that seems incorrect.   Overriding here.
            if (result == null) {
                return chain.resolveUrlPath(resourceUrlPath, locations);
            } else {
                return result;
            }
        } else {
            return chain.resolveUrlPath(resourceUrlPath, locations);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @PostConstruct
    public void initIt() throws Exception {
        if (getStrategyMap() == null || getStrategyMap().isEmpty()) {
            setStrategyMap(versionStrategyMap);
        }
    }

}
