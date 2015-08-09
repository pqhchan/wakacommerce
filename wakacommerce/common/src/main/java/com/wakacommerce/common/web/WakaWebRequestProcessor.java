package com.wakacommerce.common.web;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public interface WakaWebRequestProcessor {

    public void process(WebRequest request);

    public void postProcess(WebRequest request);

}
