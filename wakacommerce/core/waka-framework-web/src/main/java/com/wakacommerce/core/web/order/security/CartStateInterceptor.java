
package com.wakacommerce.core.web.order.security;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.web.core.CustomerState;
import com.wakacommerce.profile.web.core.security.CustomerStateInterceptor;

import javax.annotation.Resource;


/**
 * Interceptor responsible for putting the current cart on the request. Carts are defined in BLC as an {@link Order} with
 * a status of IN_PROCESS. This interceptor should go after {@link CustomerStateInterceptor} since it relies on
 * {@link CustomerState}.
 * 
 * Note that in servlet applications you should be using {@link CartStateFilter}
 * 
 *Phillip Verheyden
 * @see {@link CartState}
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
