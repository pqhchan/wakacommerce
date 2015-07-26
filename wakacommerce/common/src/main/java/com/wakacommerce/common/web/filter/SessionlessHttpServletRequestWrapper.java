
package com.wakacommerce.common.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;


public class SessionlessHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public SessionlessHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (!create) {
            return null;
        }
        throw new UnsupportedOperationException("You are in a sessionless environment and cannot get/create a HttpSession.");
    }

    @Override
    public HttpSession getSession() {
        throw new UnsupportedOperationException("You are in a sessionless environment and cannot get/create a HttpSession.");
    }

}
