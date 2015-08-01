  
package com.wakacommerce.profile.web.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.Resource;


/**
 * Interceptor responsible for putting the current customer on the current request. Note that this should always come after
 * the {@link PortletAuthenticationProcessingInterceptor} in order for this to work properly as this assumes that the
 * Spring {@link Authentication} object has already been set on Spring's {@link SecurityContext} (assuming that the user
 * is authenticated to begin with).
 * 
 *  
 * @see {@link CustomerStateRequestProcessor}
 * @see {@lnk CustomerState}
 */
public class CustomerStateInterceptor implements WebRequestInterceptor {

    @Resource(name = "blCustomerStateRequestProcessor")
    protected CustomerStateRequestProcessor customerStateProcessor;

    @Override
    public void preHandle(WebRequest request) throws Exception {
        customerStateProcessor.process(request);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        // unimplemented
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        // unimplemented
    }

}
