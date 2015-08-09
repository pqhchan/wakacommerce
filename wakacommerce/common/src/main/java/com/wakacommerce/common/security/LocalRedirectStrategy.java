
package com.wakacommerce.common.security;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class LocalRedirectStrategy implements RedirectStrategy {

    private boolean contextRelative = false;
    private static final Log LOG = LogFactory.getLog(LocalRedirectStrategy.class);
    private boolean enforcePortMatch = false;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.RedirectStrategy#sendRedirect(javax.
     * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.String)
     */
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if (!url.startsWith("/")) {
            if (StringUtils.equals(request.getParameter("successUrl"), url) || StringUtils.equals(request.getParameter("failureUrl"), url)) {
                validateRedirectUrl(request.getContextPath(), url, request.getServerName(), request.getServerPort());
            }
        }
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Redirecting to '" + url + "'");
        }

        response.sendRedirect(redirectUrl);
    }

    protected String calculateRedirectUrl(String contextPath, String url) {
        if ((!(url.startsWith("http://"))) && (!(url.startsWith("https://")))) {
            if (this.contextRelative) {
                return url;
            }
            return contextPath + url;
        }

        if (!(this.contextRelative)) {
            return url;
        }

        url = url.substring(url.indexOf("://") + 3);
        url = url.substring(url.indexOf(contextPath) + contextPath.length());

        if ((url.length() > 1) && (url.charAt(0) == '/')) {
            url = url.substring(1);
        }

        return url;
    }

    private void validateRedirectUrl(String contextPath, String url, String requestServerName, int requestServerPort) throws MalformedURLException {
        URL urlObject = new URL(url);
        if (urlObject.getProtocol().equals("http") || urlObject.getProtocol().equals("https")) {
            if (StringUtils.equals(requestServerName, urlObject.getHost())) {
                if (!enforcePortMatch || requestServerPort == urlObject.getPort()) {
                    if (StringUtils.isEmpty(contextPath) || urlObject.getPath().startsWith("/" + contextPath)) {
                        return;
                    }
                }
            }
        }
        String errorMessage = "Invalid redirect url specified.  Must be of the form /<relative view> or http[s]://<server name>[:<server port>][/<context path>]/...";
        LOG.warn(errorMessage + ":  " + url);
        throw new MalformedURLException(errorMessage + ":  " + url);
    }

    public void setEnforcePortMatch(boolean enforcePortMatch) {
        this.enforcePortMatch = enforcePortMatch;
    }

    public void setContextRelative(boolean contextRelative) {
        this.contextRelative = contextRelative;
    }

}
