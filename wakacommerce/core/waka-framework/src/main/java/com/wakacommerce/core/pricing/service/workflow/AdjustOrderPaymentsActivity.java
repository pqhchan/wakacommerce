  
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
 * The AdjustOrderPaymentsActivity is responsible for adjusting any of the order payments
 * that have already been applied to the order. This happens when order payments have
 * been applied to the order but the order total has changed. In the case of a hosted
 * gateway solution like PayPal Express Checkout, the order payment is created when the
 * customer redirects to the Review Order Page (Checkout page) and the user selects
 * a shipping method which may affect the order total. Since the Hosted Order payment
 * is unconfirmed, we need to adjust the amount on this order payment before
 * we complete checkout and confirm the payment with PayPal again.
 *
 * Another scenario this handles is if there is an unconfirmed Credit Card applied to the order.
 * This can happen if the implementation is PCI-Compliant and the Credit Card number is sent to Broadleaf
 * and will make a server to server call to the configured Payment Gateway.
 *
 * For this default implementation,
 * This algorithm will add up all the active applied payments to the order that are not of type
 * 'UNCONFIRMED' AND (payment type 'THIRD_PARTY_ACCOUNT' OR 'CREDIT_CARD')
 * The order.getTotal() minus all the applied payments that are NOT Unconfirmed and of a Third Party account
 * will then be set as the new amount that should be processed by the Third Party Account.
 *
 * Third Party Account Example:
 * 1) Initial Checkout Step
 * Order - Total = $30
 * - Order Payment (PayPal Express Checkout) - [Unconfirmed] $10
 * - Gift Card - [Unconfirmed] $10
 * - Customer Credit - [Unconfirmed] $10
 *
 * 2) Shipping Method picked and changes the order total
 * Order - Total = $35
 * - Order Payment (PayPal Express Checkout) - [Unconfirmed] $10
 * - Gift Card - [Unconfirmed] $10
 * - Customer Credit - [Unconfirmed] $10
 *
 * 3) Adjust Order Payment Activity ($35 - ($10 + $10)) = $15
 * Order - Total = $35
 * - Order Payment (PayPal Express Checkout) - [Unconfirmed] $15
 * - Gift Card - [Unconfirmed] $10
 * - Customer Credit - [Unconfirmed] $10
 *
 *  
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
