
package com.wakacommerce.core.checkout.service.workflow;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.state.RollbackFailureException;
import com.wakacommerce.core.workflow.state.RollbackHandler;


/**
 * Rollback handler to execute after an order has been marked as 'completed' and there is an exception.
 * 
 *  1. Change the status back to IN_PROCESS
 *  2. Change the order number back to null
 *  3. Change the submit date back to null
 *
 *Phillip Verheyden (phillipuniverse)
 */
@Component("blCompleteOrderRollbackHandler")
public class CompleteOrderRollbackHandler implements RollbackHandler<CheckoutSeed> {

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        CheckoutSeed seed = processContext.getSeedData();
        seed.getOrder().setStatus(OrderStatus.IN_PROCESS);
        seed.getOrder().setOrderNumber(null);
        seed.getOrder().setSubmitDate(null);
    }

}
