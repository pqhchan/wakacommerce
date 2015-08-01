package com.wakacommerce.cms.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.wakacommerce.cms.web.PageHandlerMapping;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.template.TemplateOverrideExtensionManager;
import com.wakacommerce.common.template.TemplateType;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.TemplateTypeAware;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.common.web.deeplink.DeepLinkService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WakaPageController extends WakaAbstractController implements Controller, TemplateTypeAware {

    protected static String MODEL_ATTRIBUTE_NAME="page";    
    
    @Autowired(required = false)
    @Qualifier("blPageDeepLinkService")
    protected DeepLinkService<PageDTO> deepLinkService;

    @Resource(name = "blTemplateOverrideExtensionManager")
    protected TemplateOverrideExtensionManager templateOverrideManager;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        PageDTO page = (PageDTO) request.getAttribute(PageHandlerMapping.PAGE_ATTRIBUTE_NAME);
        assert page != null;

        model.addObject(MODEL_ATTRIBUTE_NAME, page);
        model.addObject("pageFields", page.getPageFields()); // For convenience
        model.addObject("BLC_PAGE_TYPE", "page");

        String plainTextStr = (String) page.getPageFields().get("plainText");

        if (!StringUtils.isEmpty(plainTextStr)) {
            if (Boolean.valueOf(plainTextStr)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
            }
        }
        
        String templatePath = page.getTemplatePath();
        
        ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
        ExtensionResultStatusType extResult = templateOverrideManager.getProxy().getOverrideTemplate(erh, page);
        if (extResult != ExtensionResultStatusType.NOT_HANDLED) {
            templatePath = erh.getResult();
        }

        model.setViewName(templatePath);
        addDeepLink(model, deepLinkService, page);
        return model;
    }

    @Override
    public String getExpectedTemplateName(HttpServletRequest request) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        if (context != null) {
            PageDTO page = (PageDTO) request.getAttribute(PageHandlerMapping.PAGE_ATTRIBUTE_NAME);
            return page.getTemplatePath();
        }
        return "";
    }

    @Override
    public TemplateType getTemplateType(HttpServletRequest request) {
        return TemplateType.PAGE;
    }

}
