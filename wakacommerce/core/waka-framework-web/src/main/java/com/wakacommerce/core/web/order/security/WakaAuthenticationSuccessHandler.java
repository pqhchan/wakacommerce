package com.wakacommerce.core.web.order.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.wakacommerce.common.util.BLCRequestUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("blAuthenticationSuccessHandler")
public class WakaAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    protected static final String SESSION_ATTR = "SFP-ActiveID";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        
        String targetUrl = request.getParameter(getTargetUrlParameter());
        if (BLCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
            request.getSession().removeAttribute(SESSION_ATTR);
        }
        if (StringUtils.isNotBlank(targetUrl) && targetUrl.contains(":")) {
            getRedirectStrategy().sendRedirect(request, response, getDefaultTargetUrl());
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
