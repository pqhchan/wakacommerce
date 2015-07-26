
package com.wakacommerce.core.order.service.manipulation;

import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.DynamicPriceDiscreteOrderItem;
import com.wakacommerce.core.order.domain.GiftWrapOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.pricing.service.exception.PricingException;

public interface OrderItemVisitor {
    
    public void visit(OrderItem orderItem) throws PricingException;
    public void visit(BundleOrderItem bundleOrderItem) throws PricingException;
    public void visit(DiscreteOrderItem discreteOrderItem) throws PricingException;
    public void visit(DynamicPriceDiscreteOrderItem dynamicPriceDiscreteOrderItem) throws PricingException;
    public void visit(GiftWrapOrderItem giftWrapOrderItem) throws PricingException;
    
}
