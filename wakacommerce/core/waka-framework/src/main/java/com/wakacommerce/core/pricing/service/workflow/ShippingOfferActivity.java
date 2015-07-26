
package com.wakacommerce.core.pricing.service.workflow;

import javax.annotation.Resource;

import com.wakacommerce.core.offer.service.ShippingOfferService;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class ShippingOfferActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name="blShippingOfferService")
    private ShippingOfferService shippingOfferService;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        shippingOfferService.reviewOffers(order);
        context.setSeedData(order);

        return context;
    }

}
