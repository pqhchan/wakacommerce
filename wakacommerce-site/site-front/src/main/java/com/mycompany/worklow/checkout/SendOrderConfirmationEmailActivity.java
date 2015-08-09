package com.mycompany.worklow.checkout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.email.service.EmailService;
import com.wakacommerce.common.email.service.info.EmailInfo;
import com.wakacommerce.core.checkout.service.workflow.CheckoutSeed;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.util.HashMap;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class SendOrderConfirmationEmailActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    protected static final Log LOG = LogFactory.getLog(SendOrderConfirmationEmailActivity.class);

    @Resource(name = "blEmailService")
    protected EmailService emailService;
    
    @Resource(name = "blOrderConfirmationEmailInfo")
    protected EmailInfo orderConfirmationEmailInfo;
    
    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        HashMap<String, Object> vars = new HashMap<String, Object>();
        vars.put("customer", order.getCustomer());
        vars.put("orderNumber", order.getOrderNumber());
        vars.put("order", order);

        //Email service failing should not trigger rollback
        try {
            emailService.sendTemplateEmail(order.getEmailAddress(), getOrderConfirmationEmailInfo(), vars);
        } catch (Exception e) {
            LOG.error(e);
        }
        return context;
    }
    
    public EmailInfo getOrderConfirmationEmailInfo() {
        return orderConfirmationEmailInfo;
    }

    public void setOrderConfirmationEmailInfo(EmailInfo orderConfirmationEmailInfo) {
        this.orderConfirmationEmailInfo = orderConfirmationEmailInfo;
    }

}

