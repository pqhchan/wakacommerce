
package com.wakacommerce.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.wakacommerce.common.security.util.CookieUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @ hui
 */
public class EnhancedTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

    @Resource(name="blCookieUtils")
    protected CookieUtils cookieUtils;

    @Deprecated
    public EnhancedTokenBasedRememberMeServices() {}
    
    public EnhancedTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }
    
    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        MockResponse mockResponse = new MockResponse();
        super.setCookie(tokens, maxAge, request, mockResponse);
        Cookie myCookie = mockResponse.getTempCookie();
        cookieUtils.setCookieValue(response, myCookie.getName(), myCookie.getValue(), myCookie.getPath(), myCookie.getMaxAge(), myCookie.getSecure());
    }
    
    private class MockResponse implements HttpServletResponse {
        
        private Cookie tempCookie;

        public void addCookie(Cookie arg0) {
            this.tempCookie = arg0;
        }
        
        public Cookie getTempCookie() {
            return tempCookie;
        }

        public void addDateHeader(String arg0, long arg1) {
            //do nothing
        }

        public void addHeader(String arg0, String arg1) {
            //do nothing
        }

        public void addIntHeader(String arg0, int arg1) {
            //do nothing
        }

        public boolean containsHeader(String arg0) {
            return false;
        }

        public String encodeRedirectUrl(String arg0) {
            return null;
        }

        public String encodeRedirectURL(String arg0) {
            return null;
        }

        public String encodeUrl(String arg0) {
            return null;
        }

        public String encodeURL(String arg0) {
            return null;
        }

        public void sendError(int arg0, String arg1) throws IOException {
            //do nothing
        }

        public void sendError(int arg0) throws IOException {
            //do nothing
        }

        public void sendRedirect(String arg0) throws IOException {
            //do nothing
        }

        public void setDateHeader(String arg0, long arg1) {
            //do nothing
        }

        public void setHeader(String arg0, String arg1) {
            //do nothing
        }

        public void setIntHeader(String arg0, int arg1) {
            //do nothing
        }

        public void setStatus(int arg0, String arg1) {
            //do nothing
        }

        public void setStatus(int arg0) {
            //do nothing
        }

        public void flushBuffer() throws IOException {
            //do nothing
        }

        public int getBufferSize() {
            return 0;
        }

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public Locale getLocale() {
            return null;
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        public PrintWriter getWriter() throws IOException {
            return null;
        }

        public boolean isCommitted() {
            return false;
        }

        public void reset() {
            //do nothing
        }

        public void resetBuffer() {
            //do nothing
        }

        public void setBufferSize(int arg0) {
            //do nothing
        }

        public void setCharacterEncoding(String arg0) {
            //do nothing
        }

        public void setContentLength(int arg0) {
            //do nothing
        }

        public void setContentType(String arg0) {
            //do nothing
        }

        public void setLocale(Locale arg0) {
            //do nothing
        }
        
    }
}
