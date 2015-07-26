
package com.wakacommerce.core.pricing.service.workflow;

import java.util.List;

import javax.annotation.Resource;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class OfferActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name="blOfferService")
    protected OfferService offerService;

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        List<OfferCode> offerCodes = offerService.buildOfferCodeListForCustomer(order.getCustomer());
        if (offerCodes != null && !offerCodes.isEmpty()) {
            order = orderService.addOfferCodes(order, offerCodes, false);
        }

        List<Offer> offers = offerService.buildOfferListForOrder(order);
        order = offerService.applyAndSaveOffersToOrder(offers, order);
        context.setSeedData(order);

        return context;
    }

}
