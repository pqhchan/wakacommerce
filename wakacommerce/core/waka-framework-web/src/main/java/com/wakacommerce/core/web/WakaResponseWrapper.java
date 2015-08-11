
package com.wakacommerce.core.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 * @ hui
 */
public class WakaResponseWrapper implements HttpServletResponse {
    
    private HttpServletResponse response;
    private int status;
    
    public WakaResponseWrapper(HttpServletResponse response) {
        this.response = response;
    }
    
    public int getStatus() {
        return status;
    }

    public void addCookie(Cookie arg0) {
        response.addCookie(arg0);
    }

    public void addDateHeader(String arg0, long arg1) {
        response.addDateHeader(arg0, arg1);
    }

    public void addHeader(String arg0, String arg1) {
        response.addHeader(arg0, arg1);
    }

    public void addIntHeader(String arg0, int arg1) {
        response.addIntHeader(arg0, arg1);
    }

    public boolean containsHeader(String arg0) {
        return response.containsHeader(arg0);
    }

    public String encodeRedirectUrl(String arg0) {
        return response.encodeRedirectUrl(arg0);
    }

    public String encodeRedirectURL(String arg0) {
        return response.encodeRedirectURL(arg0);
    }

    public String encodeUrl(String arg0) {
        return response.encodeUrl(arg0);
    }

    public String encodeURL(String arg0) {
        return response.encodeURL(arg0);
    }

    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    public int getBufferSize() {
        return response.getBufferSize();
    }

    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    public String getContentType() {
        return response.getContentType();
    }

    public Locale getLocale() {
        return response.getLocale();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    public boolean isCommitted() {
        return response.isCommitted();
    }

    public void reset() {
        response.reset();
    }

    public void resetBuffer() {
        response.resetBuffer();
    }

    public void sendError(int arg0, String arg1) throws IOException {
        response.sendError(arg0, arg1);
    }

    public void sendError(int arg0) throws IOException {
        response.sendError(arg0);
    }

    public void sendRedirect(String arg0) throws IOException {
        response.sendRedirect(arg0);
    }

    public void setBufferSize(int arg0) {
        response.setBufferSize(arg0);
    }

    public void setCharacterEncoding(String arg0) {
        response.setCharacterEncoding(arg0);
    }

    public void setContentLength(int arg0) {
        response.setContentLength(arg0);
    }

    public void setContentType(String arg0) {
        response.setContentType(arg0);
    }

    public void setDateHeader(String arg0, long arg1) {
        response.setDateHeader(arg0, arg1);
    }

    public void setHeader(String arg0, String arg1) {
        response.setHeader(arg0, arg1);
    }

    public void setIntHeader(String arg0, int arg1) {
        response.setIntHeader(arg0, arg1);
    }

    public void setLocale(Locale arg0) {
        response.setLocale(arg0);
    }

    public void setStatus(int arg0, String arg1) {
        this.status = arg0;
        response.setStatus(arg0, arg1);
    }

    public void setStatus(int arg0) {
        this.status = arg0;
        response.setStatus(arg0);
    }

}
