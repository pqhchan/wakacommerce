
package com.wakacommerce.common.web.resource.resolver;

import org.springframework.core.Ordered;

import com.wakacommerce.common.web.resource.BroadleafResourceHttpRequestHandler;

import javax.annotation.PostConstruct;

/**
 * Constants representing out of box Broadleaf Resource Transformer ordering.
 * 
 * Used by {@link BroadleafResourceHttpRequestHandler} which sorts resolvers that 
 * implement {@link Ordered} in its {@link PostConstruct} method.
 * 
 *bpolster
 *
 */
public class BroadleafResourceTransformerOrder {

    public static int BLC_CACHE_RESOURCE_TRANSFORMER = 1000;
    public static int BLC_MINIFY_RESOURCE_TRANSFORMER = 10000;
}
