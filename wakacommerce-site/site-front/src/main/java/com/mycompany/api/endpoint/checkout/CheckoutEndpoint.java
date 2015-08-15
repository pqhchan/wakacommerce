package com.mycompany.api.endpoint.checkout;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wakacommerce.core.web.api.wrapper.OrderPaymentWrapper;
import com.wakacommerce.core.web.api.wrapper.OrderWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@RestController
@RequestMapping(value = "/cart/checkout/",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CheckoutEndpoint extends com.wakacommerce.core.web.api.endpoint.checkout.CheckoutEndpoint {

    @Override
    @RequestMapping(value = "payments", method = RequestMethod.GET)
    public List<OrderPaymentWrapper> findPaymentsForOrder(HttpServletRequest request) {
        return super.findPaymentsForOrder(request);
    }

    @Override
    @RequestMapping(value = "payment", method = RequestMethod.POST)
    public OrderPaymentWrapper addPaymentToOrder(HttpServletRequest request,
                                                 OrderPaymentWrapper wrapper) {
        return super.addPaymentToOrder(request, wrapper);
    }

    @Override
    @RequestMapping(value = "payment", method = RequestMethod.DELETE)
    public OrderWrapper removePaymentFromOrder(HttpServletRequest request,
                                               OrderPaymentWrapper wrapper) {
        return super.removePaymentFromOrder(request, wrapper);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public OrderWrapper performCheckout(HttpServletRequest request) {
        return super.performCheckout(request);
    }

}
