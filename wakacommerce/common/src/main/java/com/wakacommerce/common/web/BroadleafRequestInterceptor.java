
package com.wakacommerce.common.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.Resource;


/**
 * <p>Interceptor responsible for setting up the WakaRequestContext for the life of the request. This interceptor
 * should be the very first one in the list, as other interceptors might also use {@link WakaRequestContext}.</p>
 * 
 * <p>Note that in Servlet applications you should be using the {@link WakaRequestFilter}.</p>
 * 
 *  
 * @see {@link WakaRequestProcessor}
 * @see {@link WakaRequestContext}
 */
public class BroadleafRequestInterceptor implements WebRequestInterceptor {

    @Resource(name = "blRequestProcessor")
    protected WakaRequestProcessor requestProcessor;

    @Override
    public void preHandle(WebRequest request) throws Exception {
        requestProcessor.process(request);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        //unimplemented
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        requestProcessor.postProcess(request);
    }

}
