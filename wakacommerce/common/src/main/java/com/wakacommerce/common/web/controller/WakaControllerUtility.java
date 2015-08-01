package com.wakacommerce.common.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WakaControllerUtility {
    protected static final Log LOG = LogFactory.getLog(WakaControllerUtility.class);
    
    public static final String BLC_REDIRECT_ATTRIBUTE = "blc_redirect";
    public static final String WK_AJAX_PARAMETER = "blcAjax";
    
    /**
     * 判断指定的request是普通请求还是Ajax请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxParameter = request.getParameter(WK_AJAX_PARAMETER);
        String requestedWithHeader = request.getHeader("X-Requested-With");
        boolean result = (ajaxParameter != null && "true".equals(ajaxParameter))
                || "XMLHttpRequest".equals(requestedWithHeader);
        
        if (LOG.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder()
                .append("Request URL: [").append(request.getServletPath()).append("]")
                .append(" - ")
                .append("ajaxParam: [").append(String.valueOf(ajaxParameter)).append("]")
                .append(" - ")
                .append("X-Requested-With: [").append(requestedWithHeader).append("]")
                .append(" - ")
                .append("Returning: [").append(result).append("]");
            LOG.trace(sb.toString());
        }
        
        return result;
    }
    
}
