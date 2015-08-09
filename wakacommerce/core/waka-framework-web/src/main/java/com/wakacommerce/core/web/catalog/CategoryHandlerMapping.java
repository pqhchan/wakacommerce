
package com.wakacommerce.core.web.catalog;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.common.web.WakaAbstractHandlerMapping;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.service.CatalogService;

/**
 *
 * @ hui
 */
public class CategoryHandlerMapping extends WakaAbstractHandlerMapping {
    
    private String controllerName="blCategoryController";
    
    protected String defaultTemplateName = "catalog/category";

    @Resource(name = "blCatalogService")
    private CatalogService catalogService;
    
    public static final String CURRENT_CATEGORY_ATTRIBUTE_NAME = "category";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {      
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            String requestUri = URLDecoder.decode(context.getRequestURIWithoutContext(), charEncoding);
            Category category = catalogService.findCategoryByURI(requestUri);

            if (category != null) {
                context.getRequest().setAttribute(CURRENT_CATEGORY_ATTRIBUTE_NAME, category);
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
