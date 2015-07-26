
package com.wakacommerce.core.web.catalog;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.common.web.BLCAbstractHandlerMapping;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This handler mapping works with the Sku entity to determine if a sku has been configured for
 * the passed in URL.   
 * 
 * If the URL matches a valid Sku then this mapping returns the handler configured via the 
 * controllerName property or blSkuController by default. 
 *
 *Joshua Skorton (jskorton)
 * @since 3.2
 * @see com.wakacommerce.core.catalog.domain.Sku
 * @see CatalogService
 */
public class SkuHandlerMapping extends BLCAbstractHandlerMapping {
    
    private String controllerName="blSkuController";

    @Value("${solr.index.use.sku}")
    protected boolean useSku;
    
    @Resource(name = "blCatalogService")
    private CatalogService catalogService;

    protected String defaultTemplateName = "catalog/sku";

    public static final String CURRENT_SKU_ATTRIBUTE_NAME = "currentSku";

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        if(!useSku) {
            return null;
        }
        BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            Sku sku = catalogService.findSkuByURI(context.getRequestURIWithoutContext());
            if (sku != null) {
                context.getRequest().setAttribute(CURRENT_SKU_ATTRIBUTE_NAME, sku);
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
