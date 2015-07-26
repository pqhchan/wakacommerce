
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * This resolver is responsible for returning the Customer object that is currently present on the request.
 * 
 *Andre Azzolini (apazzolini)
 */
public interface BroadleafRequestCustomerResolver {

    public Object getCustomer(HttpServletRequest request);

    public Object getCustomer();

    public Object getCustomer(WebRequest request);

    public void setCustomer(Object customer);

    public String getCustomerRequestAttributeName();

    public void setCustomerRequestAttributeName(String customerRequestAttributeName);


}
