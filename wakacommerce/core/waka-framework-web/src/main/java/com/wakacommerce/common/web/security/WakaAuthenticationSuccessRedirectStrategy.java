package com.wakacommerce.common.web.security;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.web.controller.WakaControllerUtility;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Component("blAuthenticationSuccessRedirectStrategy")
public class WakaAuthenticationSuccessRedirectStrategy implements RedirectStrategy {
    
    private String redirectPath="/redirect";
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if (WakaControllerUtility.isAjaxRequest(request)) {
            if (BLCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
                request.getSession().setAttribute("BLC_REDIRECT_URL", url);
            }
            url = getRedirectPath();
        }
        redirectStrategy.sendRedirect(request, response, url);
    }

    public String updateLoginErrorUrlForAjax(String url) {
        String blcAjax = WakaControllerUtility.WK_AJAX_PARAMETER;
        if (url != null && url.indexOf("?") > 0) {
            url = url + "&" + blcAjax + "=true";
        } else {
            url = url + "?" + blcAjax + "=true";
        }
        return url;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
