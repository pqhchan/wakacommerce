
package com.wakacommerce.core.web.catalog;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.common.web.WakaAbstractHandlerMapping;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.service.CatalogService;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This handler mapping works with the Product entity to determine if a product has been configured for
 * the passed in URL.   
 * 
 * If the URL matches a valid Product then this mapping returns the handler configured via the 
 * controllerName property or blProductController by default. 
 *
 * 
 * @since 2.0
 * @see com.wakacommerce.core.catalog.domain.Product
 * @see CatalogService
 */
public class ProductHandlerMapping extends WakaAbstractHandlerMapping {
    
    private final String controllerName="blProductController";
    
    @Value("${solr.index.use.sku}")
    protected boolean useSku;

    @Resource(name = "blCatalogService")
    private CatalogService catalogService;

    protected String defaultTemplateName = "catalog/product";

    public static final String CURRENT_PRODUCT_ATTRIBUTE_NAME = "currentProduct";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        if(useSku) {
            return null;
        }
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            String requestUri = URLDecoder.decode(context.getRequestURIWithoutContext(), charEncoding);
            Product product = catalogService.findProductByURI(requestUri);
            if (product != null) {
                context.getRequest().setAttribute(CURRENT_PRODUCT_ATTRIBUTE_NAME, product);
                return controllerName;
            }
        }
        return null;
    }

    public String getDefaultTemplateName() {
        return defaultTemplateName;
    }

    public void setDefaultTemplateName(String defaultTemplateName) {
        this.defaultTemplateName = defaultTemplateName;
    }

}
