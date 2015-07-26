
package com.wakacommerce.core.checkout.service.workflow;

import javax.annotation.Resource;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.TaxService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 * This is an optional activity to allow a committal of taxes to a tax sub system. Many tax 
 * providers store tax details for reference, debugging, reporting, and reconciliation.
 * 
 *Kelly Tisdell
 *
 */
public class CommitTaxActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {
    
    @Resource(name = "blTaxService")
    protected TaxService taxService;

    public CommitTaxActivity() {
        super();
        //We can automatically register a rollback handler because the state will be in the process context.
        super.setAutomaticallyRegisterRollbackHandler(true);
    }

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();

        if (!order.getTaxOverride()) {
            order = taxService.commitTaxForOrder(order);
            context.getSeedData().setOrder(order);
        }

        return context;
    }

}
