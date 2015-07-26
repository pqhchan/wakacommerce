
package com.wakacommerce.core.web.catalog;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.common.web.BLCAbstractHandlerMapping;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.service.CatalogService;

/**
 * This handler mapping works with the Category entity to determine if a category has been configured for
 * the passed in URL.   
 * 
 * If the URL matches a valid Category then this mapping returns the handler configured via the 
 * controllerName property or blCategoryController by default. 
 *
 *bpolster
 * @since 2.0
 * @see com.wakacommerce.core.catalog.domain.Category
 * @see CataService
 */
public class CategoryHandlerMapping extends BLCAbstractHandlerMapping {
    
    private String controllerName="blCategoryController";
    
    protected String defaultTemplateName = "catalog/category";

    @Resource(name = "blCatalogService")
    private CatalogService catalogService;
    
    public static final String CURRENT_CATEGORY_ATTRIBUTE_NAME = "category";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request)
            throws Exception {      
        BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
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
