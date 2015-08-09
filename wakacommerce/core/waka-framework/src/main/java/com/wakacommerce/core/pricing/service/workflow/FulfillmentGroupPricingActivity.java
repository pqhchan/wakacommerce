
package com.wakacommerce.core.pricing.service.workflow;

import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.FulfillmentPricingService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.math.BigDecimal;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class FulfillmentGroupPricingActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name = "blFulfillmentPricingService")
    private FulfillmentPricingService fulfillmentPricingService;

    public void setFulfillmentPricingService(FulfillmentPricingService fulfillmentPricingService) {
        this.fulfillmentPricingService = fulfillmentPricingService;
    }

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        /*
         * 1. Get FGs from Order
         * 2. take each FG and call shipping module with the shipping svc
         * 3. add FG back to order
         */

        Money totalFulfillmentCharges = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, order.getCurrency());
        for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
            if (fulfillmentGroup != null) {
                if (!fulfillmentGroup.getShippingOverride()) {
                    fulfillmentGroup = fulfillmentPricingService.calculateCostForFulfillmentGroup(fulfillmentGroup);
                }
                if (fulfillmentGroup.getFulfillmentPrice() != null) {
                    totalFulfillmentCharges = totalFulfillmentCharges.add(fulfillmentGroup.getFulfillmentPrice());
                }
            }
        }
        order.setTotalFulfillmentCharges(totalFulfillmentCharges);
        context.setSeedData(order);

        return context;
    }

}
