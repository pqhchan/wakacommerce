  

package com.mycompany.api.endpoint.cart;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wakacommerce.core.web.api.wrapper.OrderWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * This is a reference REST API endpoint for order history. This can be modified, used as is, or removed. 
 * The purpose is to provide an out of the box RESTful order history service implementation, but also 
 * to allow the implementor to have fine control over the actual API, URIs, and general JAX-RS annotations.
 * 
 *  
 *
 */
@RestController
@RequestMapping(value = "/orders/",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class OrderHistoryEndpoint extends com.wakacommerce.core.web.api.endpoint.order.OrderHistoryEndpoint {

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderWrapper> findOrdersForCustomer(HttpServletRequest request,
            @RequestParam(value = "orderStatus", defaultValue = "SUBMITTED") String orderStatus) {
        return super.findOrdersForCustomer(request, orderStatus);
    }

}
