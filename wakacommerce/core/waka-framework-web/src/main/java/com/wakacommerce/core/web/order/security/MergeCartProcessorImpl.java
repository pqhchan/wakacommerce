package com.wakacommerce.core.web.order.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.security.MergeCartProcessor;
import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.MergeCartService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.MergeCartResponse;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.security.CustomerStateRequestProcessor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Deprecated
@Component("blMergeCartProcessor")
public class MergeCartProcessorImpl implements MergeCartProcessor {

    protected String mergeCartResponseKey = "bl_merge_cart_response";

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    @Resource(name="blOrderService")
    protected OrderService orderService;
    
    @Resource(name="blMergeCartService")
    protected MergeCartService mergeCartService;
    
    @Resource(name = "blCustomerStateRequestProcessor")
    protected CustomerStateRequestProcessor customerStateRequestProcessor;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        execute(new ServletWebRequest(request, response), authResult);
    }
    
    @Override
    public void execute(WebRequest request, Authentication authResult) {
        Customer loggedInCustomer = customerService.readCustomerByUsername(authResult.getName());
        Customer anonymousCustomer = customerStateRequestProcessor.getAnonymousCustomer(request);
        
        Order cart = null;
        if (anonymousCustomer != null) {
            cart = orderService.findCartForCustomer(anonymousCustomer);
        }
        MergeCartResponse mergeCartResponse;
        try {
            mergeCartResponse = mergeCartService.mergeCart(loggedInCustomer, cart);
        } catch (PricingException e) {
            throw new RuntimeException(e);
        } catch (RemoveFromCartException e) {
            throw new RuntimeException(e);
        }

        if (BLCRequestUtils.isOKtoUseSession(request)) {
            request.setAttribute(mergeCartResponseKey, mergeCartResponse, WebRequest.SCOPE_GLOBAL_SESSION);
        }
    }

    public String getMergeCartResponseKey() {
        return mergeCartResponseKey;
    }

    public void setMergeCartResponseKey(String mergeCartResponseKey) {
        this.mergeCartResponseKey = mergeCartResponseKey;
    }

}
