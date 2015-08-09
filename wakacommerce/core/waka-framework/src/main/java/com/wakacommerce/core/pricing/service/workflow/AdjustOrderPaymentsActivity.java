
package com.wakacommerce.core.pricing.service.workflow;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.math.BigDecimal;

/**
 *
 * @ hui
 */
public class AdjustOrderPaymentsActivity extends BaseActivity<ProcessContext<Order>> {

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        OrderPayment unconfirmedThirdPartyOrCreditCard = null;
        Money appliedPaymentsWithoutThirdPartyOrCC = Money.ZERO;
        for (OrderPayment payment : order.getPayments()) {
            if (payment.isActive()) {
                if (!payment.isConfirmed() && payment.isFinalPayment())  {
                    unconfirmedThirdPartyOrCreditCard = payment;
                } else if (payment.getAmount() != null) {
                    appliedPaymentsWithoutThirdPartyOrCC = appliedPaymentsWithoutThirdPartyOrCC.add(payment.getAmount());
                }
            }

        }

        if (unconfirmedThirdPartyOrCreditCard != null) {
            Money difference = order.getTotal().subtract(appliedPaymentsWithoutThirdPartyOrCC);
            unconfirmedThirdPartyOrCreditCard.setAmount(difference);
        }

        context.setSeedData(order);
        return context;
    }

}
