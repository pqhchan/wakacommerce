
package com.wakacommerce.core.web.controller.catalog;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.wakacommerce.common.template.TemplateType;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.TemplateTypeAware;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.common.web.deeplink.DeepLinkService;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.web.catalog.SkuHandlerMapping;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafSkuController extends WakaAbstractController implements Controller, TemplateTypeAware {
    
    protected String defaultSkuView = "catalog/sku";
    protected static String MODEL_ATTRIBUTE_NAME = "sku";
    protected static String ALL_SKUS_ATTRIBUTE_NAME = "blcAllDisplayedSkus";
    
    @Autowired(required = false)
    @Qualifier("blSkuDeepLinkService")
    protected DeepLinkService<Sku> deepLinkService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        Sku sku = (Sku) request.getAttribute(SkuHandlerMapping.CURRENT_SKU_ATTRIBUTE_NAME);
        assert(sku != null);
        
        model.addObject(MODEL_ATTRIBUTE_NAME, sku);
        Set<Sku> allSkusSet = new HashSet<Sku>();
        allSkusSet.add(sku);
        model.addObject(ALL_SKUS_ATTRIBUTE_NAME, new HashSet<Sku>(allSkusSet));

        addDeepLink(model, deepLinkService, sku);

        if (StringUtils.isNotEmpty(sku.getDisplayTemplate())) {
            model.setViewName(sku.getDisplayTemplate());    
        } else {
            model.setViewName(getDefaultSkuView());
        }
        return model;
    }

    public String getDefaultSkuView() {
        return defaultSkuView;
    }

    public void setDefaultSkuView(String defaultSkuView) {
        this.defaultSkuView = defaultSkuView;
    }
    
    @Override
    public String getExpectedTemplateName(HttpServletRequest request) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null) {
            Sku sku = (Sku) context.getRequest().getAttribute(SkuHandlerMapping.CURRENT_SKU_ATTRIBUTE_NAME);
            if (sku != null && sku.getDisplayTemplate() != null) {
                return sku.getDisplayTemplate();
            }
        }
        return getDefaultSkuView();
    }

    @Override
    public TemplateType getTemplateType(HttpServletRequest request) {
        return TemplateType.SKU;
    }

}
