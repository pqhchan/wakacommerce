package com.wakacommerce.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.presentation.AdminPresentation;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class RequestDTOImpl implements RequestDTO, Serializable {

    private static final long serialVersionUID = 1L;

    @AdminPresentation(friendlyName = "RequestDTOImpl_Request_URI")
    private String requestURI;

    @AdminPresentation(friendlyName = "RequestDTOImpl_Full_Url")
    private String fullUrlWithQueryString;

    @AdminPresentation(friendlyName = "RequestDTOImpl_Is_Secure")
    private Boolean secure;

    public RequestDTOImpl(HttpServletRequest request) {
        requestURI = request.getRequestURI();
        fullUrlWithQueryString = request.getRequestURL().toString();
        if (StringUtils.isNotEmpty(request.getQueryString())) {
            fullUrlWithQueryString += "?" + request.getQueryString();
        }
        secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
    }

    public RequestDTOImpl(WebRequest request) {
        // Page level targeting does not work for WebRequest.
        secure = request.isSecure();
    }

    /**
     * @return  returns the request not including the protocol, domain, or query string
     */
    @Override
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * @return Returns the URL and parameters.
     */
    @Override
    public String getFullUrLWithQueryString() {
        return fullUrlWithQueryString;
    }

    /**
     * @return true if this request came in through HTTPS
     */
    @Override
    public Boolean isSecure() {
        return secure;
    }

    public String getFullUrlWithQueryString() {
        return fullUrlWithQueryString;
    }

    public void setFullUrlWithQueryString(String fullUrlWithQueryString) {
        this.fullUrlWithQueryString = fullUrlWithQueryString;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

}
