
package com.wakacommerce.core.web.controller.catalog;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.template.TemplateOverrideExtensionManager;
import com.wakacommerce.common.template.TemplateType;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.TemplateTypeAware;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.common.web.deeplink.DeepLinkService;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.web.catalog.ProductHandlerMapping;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafProductController extends WakaAbstractController implements Controller, TemplateTypeAware {
    
    protected String defaultProductView = "catalog/product";
    protected static String MODEL_ATTRIBUTE_NAME = "product";
    protected static String ALL_PRODUCTS_ATTRIBUTE_NAME = "blcAllDisplayedProducts";
    
    @Autowired(required = false)
    @Qualifier("blProductDeepLinkService")
    protected DeepLinkService<Product> deepLinkService;
    
    @Resource(name = "blTemplateOverrideExtensionManager")
    protected TemplateOverrideExtensionManager templateOverrideManager;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        Product product = (Product) request.getAttribute(ProductHandlerMapping.CURRENT_PRODUCT_ATTRIBUTE_NAME);
        assert(product != null);
        
        model.addObject(MODEL_ATTRIBUTE_NAME, product);
        Set<Product> allProductsSet = new HashSet<Product>();
        allProductsSet.add(product);
        model.addObject(ALL_PRODUCTS_ATTRIBUTE_NAME, new HashSet<Product>(allProductsSet));
        model.addObject("BLC_PAGE_TYPE", "product");

        addDeepLink(model, deepLinkService, product);
        
        String templatePath = null;

        // Use the products custom template if available
        if (StringUtils.isNotBlank(product.getDisplayTemplate())) {
            templatePath = product.getDisplayTemplate();
        } else {
            // Otherwise, use the controller default.
            templatePath = getDefaultProductView();
        }

        // Allow extension managers to override.
        ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
        ExtensionResultStatusType extResult = templateOverrideManager.getProxy().getOverrideTemplate(erh, product);
        if (extResult != ExtensionResultStatusType.NOT_HANDLED) {
            templatePath = erh.getResult();
        }
        
        model.setViewName(templatePath);
        return model;
    }

    public String getDefaultProductView() {
        return defaultProductView;
    }

    public void setDefaultProductView(String defaultProductView) {
        this.defaultProductView = defaultProductView;
    }
    
    @Override
    public String getExpectedTemplateName(HttpServletRequest request) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null) {
            Product product = (Product) context.getRequest().getAttribute(ProductHandlerMapping.CURRENT_PRODUCT_ATTRIBUTE_NAME);
            if (product != null && product.getDisplayTemplate() != null) {
                return product.getDisplayTemplate();
            }
        }
        return getDefaultProductView();
    }

    @Override
    public TemplateType getTemplateType(HttpServletRequest request) {
        return TemplateType.PRODUCT;
    }

}
