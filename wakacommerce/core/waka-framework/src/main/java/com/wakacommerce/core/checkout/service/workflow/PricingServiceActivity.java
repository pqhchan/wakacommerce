
package com.wakacommerce.core.checkout.service.workflow;

import javax.annotation.Resource;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.PricingService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class PricingServiceActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name="blPricingService")
    private PricingService pricingService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        Order order = pricingService.executePricing(seed.getOrder());
        seed.setOrder(order);

        return context;
    }

}
