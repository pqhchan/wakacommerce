package com.wakacommerce.profile.web.core.taglib;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CustomerInfoTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    private String var;

    public int doStartTag() throws JspException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        CustomerState customerState = (CustomerState) applicationContext.getBean("blCustomerState");
        Customer customer = customerState.getCustomer((HttpServletRequest) pageContext.getRequest());
        pageContext.setAttribute(var, customer);

        return EVAL_PAGE;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
