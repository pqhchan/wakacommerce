  

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
 *
 * @ hui
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
