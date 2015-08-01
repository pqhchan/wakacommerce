  
package com.wakacommerce.profile.web.core.security;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("blCustomerStateFilter")
/**
 * <p>
 * This filter should be configured after the RememberMe listener from Spring Security.
 * Retrieves the Broadleaf Customer based using the authenticated user OR creates an Anonymous customer and stores them
 * in the session.  Calls Customer.setCookied(true) if the authentication token is an instance of
 * {@link org.springframework.security.providers.rememberme.RememberMeAuthenticationToken).   Calls Customer.setLoggedIn(true) if
 * the authentication token is an instance of {@link org.springframework.security.providers.UsernamePasswordAuthenticationToken}
 * </p>
 *
 * 
 */
public class CustomerStateFilter extends OncePerRequestFilter implements Ordered {
    
    @Resource(name="blCustomerStateRequestProcessor")
    protected CustomerStateRequestProcessor customerStateProcessor;

    @Override
    public void doFilterInternal(HttpServletRequest baseRequest, HttpServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        ServletWebRequest request = new ServletWebRequest(baseRequest, baseResponse);
        try {
            customerStateProcessor.process(request);
            chain.doFilter(baseRequest, baseResponse);
        } finally {
            customerStateProcessor.postProcess(request);
        }
    }

    @Override
    public int getOrder() {
        //FilterChainOrder has been dropped from Spring Security 3
        //return FilterChainOrder.REMEMBER_ME_FILTER+1;
        return 1501;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
