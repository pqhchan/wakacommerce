
package com.wakacommerce.core.web.catalog;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.common.web.WakaAbstractHandlerMapping;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class SkuHandlerMapping extends WakaAbstractHandlerMapping {
    
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
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
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
