
package com.wakacommerce.core.pricing.service.workflow;

import org.apache.commons.lang.StringUtils;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import com.wakacommerce.common.rule.MvelHelper;
import com.wakacommerce.common.util.EfficientLRUMap;
import com.wakacommerce.core.catalog.domain.SkuFee;
import com.wakacommerce.core.catalog.service.type.SkuFeeType;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupFee;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.FulfillmentGroupService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 
 *Phillip Verheyden
 */
public class ConsolidateFulfillmentFeesActivity extends BaseActivity<ProcessContext<Order>> {
    
    @SuppressWarnings("unchecked")
    protected static final Map EXPRESSION_CACHE = new EfficientLRUMap(1000);
    
    @Resource(name = "blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        
        for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
            //create and associate all the Fulfillment Fees
            for (FulfillmentGroupItem item : fulfillmentGroup.getFulfillmentGroupItems()) {
                List<SkuFee> fees = null;
                if (item.getOrderItem() instanceof BundleOrderItem) {
                    fees = ((BundleOrderItem)item.getOrderItem()).getSku().getFees();
                } else if (item.getOrderItem() instanceof DiscreteOrderItem) {
                    fees = ((DiscreteOrderItem)item.getOrderItem()).getSku().getFees();
                }
                
                if (fees != null) {
                    for (SkuFee fee : fees) {
                        if (SkuFeeType.FULFILLMENT.equals(fee.getFeeType())) {
                            if (shouldApplyFeeToFulfillmentGroup(fee, fulfillmentGroup)) {
                                FulfillmentGroupFee fulfillmentFee = fulfillmentGroupService.createFulfillmentGroupFee();
                                fulfillmentFee.setName(fee.getName());
                                fulfillmentFee.setTaxable(fee.getTaxable());
                                fulfillmentFee.setAmount(fee.getAmount());
                                fulfillmentFee.setFulfillmentGroup(fulfillmentGroup);
                                
                                fulfillmentGroup.addFulfillmentGroupFee(fulfillmentFee);
                            }
                        }
                    }
                }
            }
            
            if (fulfillmentGroup.getFulfillmentGroupFees().size() > 0) {
                fulfillmentGroup = fulfillmentGroupService.save(fulfillmentGroup);
            }
        }
        
        context.setSeedData(order);
        return context;
    }

    /**
     * If the SkuFee expression is null or empty, this method will always return true
     * 
     * @param fee
     * @param fulfillmentGroup
     * @return
     */
    protected boolean shouldApplyFeeToFulfillmentGroup(SkuFee fee, FulfillmentGroup fulfillmentGroup) {
        boolean appliesToFulfillmentGroup = true;
        String feeExpression = fee.getExpression();
        
        if (!StringUtils.isEmpty(feeExpression)) {
            Serializable exp = (Serializable) EXPRESSION_CACHE.get(feeExpression);
            if (exp == null) {
                ParserContext mvelContext = new ParserContext();
                mvelContext.addImport("MVEL", MVEL.class);
                mvelContext.addImport("MvelHelper", MvelHelper.class);
                exp = MVEL.compileExpression(feeExpression, mvelContext);
            
                EXPRESSION_CACHE.put(feeExpression, exp);
            }
            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put("fulfillmentGroup", fulfillmentGroup);
            return (Boolean)MVEL.executeExpression(feeExpression, vars);
        }
        
        return appliesToFulfillmentGroup;
    }

}
