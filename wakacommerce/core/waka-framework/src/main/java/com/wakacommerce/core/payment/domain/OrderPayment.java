
package com.wakacommerce.core.payment.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.payment.domain.secure.Referenced;
import com.wakacommerce.profile.core.domain.Address;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ hui
 */
public interface OrderPayment extends Serializable, Status {

    public Long getId();

    public void setId(Long id);

    public Order getOrder();

    public void setOrder(Order order);

    public Address getBillingAddress();

    public void setBillingAddress(Address billingAddress);

    public Money getAmount();

    public void setAmount(Money amount);

    public String getReferenceNumber();

    public void setReferenceNumber(String referenceNumber);

    public PaymentType getType();

    public void setType(PaymentType type);

    public PaymentGatewayType getGatewayType();

    public void setPaymentGatewayType(PaymentGatewayType gatewayType);

    public List<PaymentTransaction> getTransactions();

    public void setTransactions(List<PaymentTransaction> details);

    public void addTransaction(PaymentTransaction transaction);

    public List<PaymentTransaction> getTransactionsForType(PaymentTransactionType type);

    public PaymentTransaction getInitialTransaction();

    public Money getTransactionAmountForType(PaymentTransactionType type);

    public Money getSuccessfulTransactionAmountForType(PaymentTransactionType type);

    public boolean isConfirmed();

    public boolean isFinalPayment();

    public BroadleafCurrency getCurrency();

}
