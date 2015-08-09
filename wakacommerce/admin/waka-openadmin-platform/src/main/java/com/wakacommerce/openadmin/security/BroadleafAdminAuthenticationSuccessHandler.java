
package com.wakacommerce.openadmin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.wakacommerce.common.web.BroadleafSandBoxResolver;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.remote.SecurityVerifier;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafAdminAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    protected String loginUri = "/login"; // default login uri but can be overridden in admin security config
    private RequestCache requestCache = new HttpSessionRequestCache();
    private static final String successUrlParameter = "successUrl=";

    @Resource(name = "blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        AdminUser user = adminRemoteSecurityService.getPersistentAdminUser();
        if (user != null && user.getLastUsedSandBoxId() != null) {
            request.getSession(false).setAttribute(BroadleafSandBoxResolver.SANDBOX_ID_VAR, user.getLastUsedSandBoxId());
        }

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();

        // Remove the sessionTimeout flag if necessary
        targetUrl = targetUrl.replace("sessionTimeout=true", "");
        if (targetUrl.charAt(targetUrl.length() - 1) == '?') {
            targetUrl = targetUrl.substring(0, targetUrl.length() - 1);
        }

        if (targetUrl.contains(successUrlParameter)) {
            int successUrlPosistion = targetUrl.indexOf(successUrlParameter) + successUrlParameter.length();
            int nextParamPosistion = targetUrl.indexOf("&", successUrlPosistion);
            if (nextParamPosistion == -1) {
                targetUrl = targetUrl.substring(successUrlPosistion, targetUrl.length());
            } else {
                targetUrl = targetUrl.substring(successUrlPosistion, nextParamPosistion);
            }
        }

        // Remove the login URI so we don't continuously redirect to the login page
        targetUrl = removeLoginSegment(targetUrl);

        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String removeLoginSegment(String url) {
        if (StringUtils.isEmpty(url)) {
            return "/";
        }
        int lastSlashPos = url.lastIndexOf(loginUri);
        if (lastSlashPos >= 0) {
            return url.substring(0, lastSlashPos);
        } else {
            return url;
        }
    }

    public String getLoginUri() {
        return loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri;
    }
}
