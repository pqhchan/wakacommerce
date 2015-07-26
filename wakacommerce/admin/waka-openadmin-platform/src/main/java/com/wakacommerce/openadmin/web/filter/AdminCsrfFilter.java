
package com.wakacommerce.openadmin.web.filter;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.security.handler.CsrfFilter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class attempts the work flow of the CsrfFilter, but in the event of a Csrf token mismatch 
 * (Session reset for example) the User will be redirected to login, if not session reset User is sent to previous location.
 * 
 * The "blCsrfFilter' from applicationContext-admin-security should reference this class (com.wakacommerce.openadmin.web.filter.AdminCsrfFilter)
 * instead of the CsrfFilter
 * 
 *     <bean id="blCsrfFilter" class="com.wakacommerce.openadmin.web.filter.AdminCsrfFilter" />
 *     
 *trevorleffert
 */
public class AdminCsrfFilter extends CsrfFilter {
    
    @Resource(name = "blAdminAuthenticationFailureHandler")
    protected AuthenticationFailureHandler failureHandler;
    
    public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(baseRequest, baseResponse, chain);
        } catch (ServletException e) {
            if (e.getCause() instanceof ServiceException) {
                HttpServletRequest baseHttpRequest = (HttpServletRequest) baseRequest;
                //if authentication is null and CSRF token is invalid, must be session time out
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    baseHttpRequest.setAttribute("sessionTimeout", true);
                    failureHandler.onAuthenticationFailure((HttpServletRequest) baseRequest, (HttpServletResponse) baseResponse, new SessionAuthenticationException("Session Time Out"));
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }
}
