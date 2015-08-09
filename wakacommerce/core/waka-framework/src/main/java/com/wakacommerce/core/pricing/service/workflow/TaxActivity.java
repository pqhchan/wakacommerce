
package com.wakacommerce.core.pricing.service.workflow;

import javax.annotation.Resource;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.TaxService;
import com.wakacommerce.core.pricing.service.module.TaxModule;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 *
 * @ hui
 */
public class TaxActivity extends BaseActivity<ProcessContext<Order>> {

    protected TaxModule taxModule;

    @Resource(name = "blTaxService")
    protected TaxService taxService;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        if (taxService != null) {
            order = taxService.calculateTaxForOrder(order);
        } else if (taxModule != null) {
            order = taxModule.calculateTaxForOrder(order);
        }

        context.setSeedData(order);
        return context;
    }

    public void setTaxModule(TaxModule taxModule) {
        this.taxModule = taxModule;
    }

    public void setTaxService(TaxService taxService) {
        this.taxService = taxService;
    }

}
