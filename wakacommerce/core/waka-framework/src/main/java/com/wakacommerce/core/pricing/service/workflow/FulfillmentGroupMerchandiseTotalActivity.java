
package com.wakacommerce.core.pricing.service.workflow;

import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.math.BigDecimal;

/**
 *
 * @ hui
 */
public class FulfillmentGroupMerchandiseTotalActivity extends BaseActivity<ProcessContext<Order>> {

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        for(FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
            Money merchandiseTotal = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, fulfillmentGroup.getOrder().getCurrency());
            for(FulfillmentGroupItem fulfillmentGroupItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                OrderItem item = fulfillmentGroupItem.getOrderItem();
                merchandiseTotal = merchandiseTotal.add(item.getTotalPrice());
            }
            fulfillmentGroup.setMerchandiseTotal(merchandiseTotal);
        }
        context.setSeedData(order);

        return context;
    }

}
