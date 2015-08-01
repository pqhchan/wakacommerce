package com.wakacommerce.profile.web.core.taglib;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerPhoneService;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CustomerPhoneTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    private Long customerPhoneId;
    private String var;

    public int doStartTag() throws JspException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        CustomerState customerState = (CustomerState) applicationContext.getBean("blCustomerState");
        CustomerPhoneService customerPhoneService = (CustomerPhoneService) applicationContext.getBean("blCustomerPhoneService");

        Customer customer = customerState.getCustomer((HttpServletRequest) pageContext.getRequest());

        if(customerPhoneId != null){
            pageContext.setAttribute(var, customerPhoneService.readCustomerPhoneById(customerPhoneId));
        }else{
            pageContext.setAttribute(var, customerPhoneService.readActiveCustomerPhonesByCustomerId(customer.getId()));
        }

        return EVAL_PAGE;
    }

    public Long getCustomerPhoneId() {
        return customerPhoneId;
    }

    public String getVar() {
        return var;
    }

    public void setCustomerPhoneId(Long customerPhoneId) {
        this.customerPhoneId = customerPhoneId;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
