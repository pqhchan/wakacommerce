package com.wakacommerce.core.web.order.security;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.web.core.CustomerState;
import com.wakacommerce.profile.web.core.security.CustomerStateInterceptor;

import javax.annotation.Resource;


/**
 *
 * @ hui
 */
public class CartStateInterceptor implements WebRequestInterceptor {

    @Resource(name = "blCartStateRequestProcessor")
    protected CartStateRequestProcessor cartStateProcessor;

    @Override
    public void preHandle(WebRequest request) throws Exception {
        cartStateProcessor.process(request);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        // unimplemented
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        // unimplemented
    }

}
