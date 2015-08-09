  

package com.mycompany.api.endpoint.cart;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wakacommerce.core.web.api.wrapper.FulfillmentGroupItemWrapper;
import com.wakacommerce.core.web.api.wrapper.FulfillmentGroupWrapper;
import com.wakacommerce.core.web.api.wrapper.FulfillmentOptionWrapper;
import com.wakacommerce.core.web.api.wrapper.OrderWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@RestController
@RequestMapping(value = "/cart/fulfillment/",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class FulfillmentEndpoint extends com.wakacommerce.core.web.api.endpoint.order.FulfillmentEndpoint {

    @Override
    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public List<FulfillmentGroupWrapper> findFulfillmentGroupsForOrder(HttpServletRequest request) {
        return super.findFulfillmentGroupsForOrder(request);
    }

    @Override
    @RequestMapping(value = "groups", method = RequestMethod.DELETE)
    public OrderWrapper removeAllFulfillmentGroupsFromOrder(HttpServletRequest request,
            @RequestParam(value = "priceOrder", defaultValue = "true") boolean priceOrder) {
        return super.removeAllFulfillmentGroupsFromOrder(request, priceOrder);
    }

    @Override
    @RequestMapping(value = "group", method = RequestMethod.POST)
    public FulfillmentGroupWrapper addFulfillmentGroupToOrder(HttpServletRequest request,
            FulfillmentGroupWrapper wrapper,
            @RequestParam(value = "priceOrder", defaultValue = "true") boolean priceOrder) {
        return super.addFulfillmentGroupToOrder(request, wrapper, priceOrder);
    }

    @Override
    @RequestMapping(value = "group/{fulfillmentGroupId}", method = RequestMethod.PUT)
    public FulfillmentGroupWrapper addItemToFulfillmentGroup(HttpServletRequest request,
            @PathVariable("fulfillmentGroupId") Long fulfillmentGroupId,
            FulfillmentGroupItemWrapper wrapper,
            @RequestParam(value = "priceOrder", defaultValue = "true") boolean priceOrder) {
        return super.addItemToFulfillmentGroup(request, fulfillmentGroupId, wrapper, priceOrder);
    }

	@Override
	@RequestMapping(value = "group/{fulfillmentGroupId}/option/{fulfillmentOptionId}", method = RequestMethod.PUT)
	public FulfillmentGroupWrapper addFulfillmentOptionToFulfillmentGroup(
			HttpServletRequest request, 
			@PathVariable("fulfillmentGroupId") Long fulfillmentGroupId,
			@PathVariable("fulfillmentOptionId") Long fulfillmentOptionId, 
            @RequestParam(value = "priceOrder", defaultValue = "true") boolean priceOrder) {
		return super.addFulfillmentOptionToFulfillmentGroup(request,
				fulfillmentGroupId, fulfillmentOptionId, priceOrder);
	}

	@Override
    @RequestMapping(value = "options", method = RequestMethod.GET)
	public List<FulfillmentOptionWrapper> findFulfillmentOptions(
			HttpServletRequest request, 
			@RequestParam("fulfillmentType") String fulfillmentType) {
		return super.findFulfillmentOptions(request, fulfillmentType);
	}

    
}
