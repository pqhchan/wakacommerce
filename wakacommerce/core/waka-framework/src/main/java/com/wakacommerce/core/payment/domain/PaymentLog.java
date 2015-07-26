
package com.wakacommerce.core.payment.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentLogEventType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.profile.core.domain.Customer;

import java.io.Serializable;
import java.util.Date;

/**
 * @deprecated - payment logs should now be captured as raw responses in Payment Transaction line items
 */
@Deprecated
public interface PaymentLog extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getUserName();

    public void setUserName(String userName);

    public Date getTransactionTimestamp();

    public void setTransactionTimestamp(Date transactionTimestamp);

    public Long getPaymentInfoId();

    public void setPaymentInfoId(Long paymentInfoId);

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public String getPaymentInfoReferenceNumber();

    public void setPaymentInfoReferenceNumber(String paymentInfoReferenceNumber);

    public PaymentTransactionType getTransactionType();

    public void setTransactionType(PaymentTransactionType transactionType);

    public Boolean getTransactionSuccess();

    public void setTransactionSuccess(Boolean transactionSuccess);

    public String getExceptionMessage();

    public void setExceptionMessage(String exceptionMessage);

    public PaymentLogEventType getLogType();

    public void setLogType(PaymentLogEventType logType);

    public Money getAmountPaid();

    public void setAmountPaid(Money amountPaid);

    void setCurrency(BroadleafCurrency currency);

    BroadleafCurrency getCurrency();

}
