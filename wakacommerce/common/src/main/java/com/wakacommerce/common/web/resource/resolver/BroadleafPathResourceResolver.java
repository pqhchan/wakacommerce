  
package com.wakacommerce.common.web.resource.resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Wraps Spring's {@link PathResourceResolver} for ordering purposes.
 *  
 *  
 * @since Broadleaf 4.0
 */
@Component("blPathResourceResolver")
public class BroadleafPathResourceResolver extends PathResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BroadleafPathResourceResolver.class);

    private int order = BroadleafResourceResolverOrder.BLC_PATH_RESOURCE_RESOLVER;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}
