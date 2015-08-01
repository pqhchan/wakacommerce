
package com.wakacommerce.common.web.resource.transformer;

import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceTransformer;
import org.springframework.web.servlet.resource.ResourceTransformerChain;

import com.wakacommerce.common.resource.service.ResourceMinificationService;
import com.wakacommerce.common.web.resource.resolver.BroadleafResourceTransformerOrder;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@link org.springframework.web.servlet.resource.ResourceTransformer} that minifies
 * the resource.    Only works with allowed extensions (".css" and ".js" by default).
 * 
 * {@link com.wakacommerce.common.resource.service.ResourceMinificationService} is used to
 * perform the minification. 
 *
 *  
 * @since 4.0
 */
@Component("blMinifyResourceTransformer")
public class MinifyResourceTransformer implements ResourceTransformer, Ordered {

    @javax.annotation.Resource(name = "blResourceMinificationService")
    protected ResourceMinificationService minifyService;

    private int order = BroadleafResourceTransformerOrder.BLC_MINIFY_RESOURCE_TRANSFORMER;

    @Override
    public Resource transform(HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain)
            throws IOException {

        Resource transformed = transformerChain.transform(request, resource);

        return minifyService.minify(transformed);
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
