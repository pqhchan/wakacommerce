
package com.wakacommerce.core.checkout.service.workflow;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.wakacommerce.common.event.OrderSubmittedEvent;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompleteOrderActivity extends BaseActivity<ProcessContext<CheckoutSeed>> implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    public CompleteOrderActivity() {
        //no specific state to set here for the rollback handler; it's always safe for it to run
        setAutomaticallyRegisterRollbackHandler(true);
    }

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();

        seed.getOrder().setStatus(getCompletedStatus());
        seed.getOrder().setOrderNumber(determineOrderNumber(seed.getOrder()));
        seed.getOrder().setSubmitDate(determineSubmitDate(seed.getOrder()));

        OrderSubmittedEvent event = new OrderSubmittedEvent(seed.getOrder().getId(), seed.getOrder().getOrderNumber());
        applicationContext.publishEvent(event);

        return context;
    }

    protected Date determineSubmitDate(Order order) {
        return Calendar.getInstance().getTime();
    }

    protected String determineOrderNumber(Order order) {
        return new SimpleDateFormat("yyyyMMddHHmmssS").format(SystemTime.asDate()) + order.getId();
    }

    protected OrderStatus getCompletedStatus() {
        return OrderStatus.SUBMITTED;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
