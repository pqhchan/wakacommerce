package com.wakacommerce.common.web;

import javax.servlet.http.HttpServletRequest;

import com.wakacommerce.common.template.TemplateType;

public interface TemplateTypeAware {

    public abstract String getExpectedTemplateName(HttpServletRequest request);

    public abstract TemplateType getTemplateType(HttpServletRequest request);

}
