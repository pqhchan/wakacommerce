
package com.wakacommerce.core.checkout.service.workflow;

import org.springframework.stereotype.Component;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.TaxService;
import com.wakacommerce.core.pricing.service.exception.TaxException;
import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.state.RollbackFailureException;
import com.wakacommerce.core.workflow.state.RollbackHandler;

import java.util.Map;

import javax.annotation.Resource;

@Component("blCommitTaxRollbackHandler")
public class CommitTaxRollbackHandler implements RollbackHandler<CheckoutSeed> {

    @Resource(name = "blTaxService")
    protected TaxService taxService;

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        ProcessContext<CheckoutSeed> ctx = processContext;
        Order order = ctx.getSeedData().getOrder();
        try {
            taxService.cancelTax(order);
        } catch (TaxException e) {
            throw new RollbackFailureException("An exception occured cancelling taxes for order id: " + order.getId(), e);
        }

    }

}
