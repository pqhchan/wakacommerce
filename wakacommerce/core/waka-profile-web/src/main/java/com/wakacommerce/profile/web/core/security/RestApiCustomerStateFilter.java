  
package com.wakacommerce.profile.web.core.security;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.filter.GenericFilterBean;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.CustomerState;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class RestApiCustomerStateFilter extends GenericFilterBean implements Ordered {

    protected static final Log LOG = LogFactory.getLog(RestApiCustomerStateFilter.class);
    
    @Resource(name="blCustomerService")
    protected CustomerService customerService;
    
    protected String customerIdAttributeName = "customerId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        String customerId = null;

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        
        //If someone already set the customer on the request then we don't need to do anything.
        if (request.getAttribute(CustomerStateRequestProcessor.getCustomerRequestAttributeName()) == null){
    
            //First check to see if someone already put the customerId on the request
            if (request.getAttribute(customerIdAttributeName) != null) {
                customerId = String.valueOf(request.getAttribute(customerIdAttributeName));
            }
            
            if (customerId == null) {
                //If it's not on the request attribute, try the parameter
                customerId = servletRequest.getParameter(customerIdAttributeName);
            }
            
            if (customerId == null) {
                //If it's not on the request parameter, look on the header
                customerId = request.getHeader(customerIdAttributeName);
            }
            
            if (customerId != null && customerId.trim().length() > 0) {
                
                if (NumberUtils.isNumber(customerId)) {
                    //If we found it, look up the customer and put it on the request.
                    Customer customer = customerService.readCustomerById(Long.valueOf(customerId));
                    if (customer != null) {
                        CustomerState.setCustomer(customer);
                    }
                } else {
                    LOG.warn(String.format("The customer id passed in '%s' was not a number", customerId));
                }
            }
            
            if (customerId == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No customer ID was found for the API request. In order to look up a customer for the request" +
                            " send a request parameter or request header for the '" + customerIdAttributeName + "' attribute");
                }
            }
        }

        filterChain.doFilter(request, servletResponse);

    }

    @Override
    public int getOrder() {
        return 2000;
    }

    public String getCustomerIdAttributeName() {
        return customerIdAttributeName;
    }

    public void setCustomerIdAttributeName(String customerIdAttributeName) {
        if (customerIdAttributeName == null || customerIdAttributeName.trim().length() < 1) {
            throw new IllegalArgumentException("customerIdAttributeName cannot be null");
        }
        this.customerIdAttributeName = customerIdAttributeName;
    }
}
