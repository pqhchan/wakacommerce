package com.wakacommerce.common.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.context.request.ServletWebRequest;

import com.wakacommerce.common.util.BLCRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class WakaRedirectController {
    
    public String redirect(HttpServletRequest request, HttpServletResponse response, Model model) {
        String path = null;
        if (BLCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
            path = (String) request.getSession().getAttribute("BLC_REDIRECT_URL");
        }

        if (path == null) {
            path = request.getContextPath();
        }
        return "ajaxredirect:" + path;
    }
}
