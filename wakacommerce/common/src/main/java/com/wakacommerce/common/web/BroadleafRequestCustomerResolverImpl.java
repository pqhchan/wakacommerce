
package com.wakacommerce.common.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * By default, we'll resolve the customer from the "customer" attribute on the request.
 * 
 * 
 */
@Service("blRequestCustomerResolver")
public class BroadleafRequestCustomerResolverImpl implements ApplicationContextAware, BroadleafRequestCustomerResolver {
    
    private static ApplicationContext applicationContext;

    protected static String customerRequestAttributeName = "customer";

    @Override
    public Object getCustomer(HttpServletRequest request) {
        return getCustomer(new ServletWebRequest(request));
    }
    
    @Override
    public Object getCustomer() {
        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        return getCustomer(request);
    }

    @Override
    public Object getCustomer(WebRequest request) {
        return request.getAttribute(getCustomerRequestAttributeName(), WebRequest.SCOPE_REQUEST);
    }

    @Override
    public void setCustomer(Object customer) {
        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        request.setAttribute(getCustomerRequestAttributeName(), customer, WebRequest.SCOPE_REQUEST);
    }

    @Override
    public String getCustomerRequestAttributeName() {
        return customerRequestAttributeName;
    }

    @Override
    public void setCustomerRequestAttributeName(String customerRequestAttributeName) {
        BroadleafRequestCustomerResolverImpl.customerRequestAttributeName = customerRequestAttributeName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BroadleafRequestCustomerResolverImpl.applicationContext = applicationContext;
    }
    
    public static BroadleafRequestCustomerResolver getRequestCustomerResolver() {
        return (BroadleafRequestCustomerResolver) applicationContext.getBean("blRequestCustomerResolver");
    }
    
}
