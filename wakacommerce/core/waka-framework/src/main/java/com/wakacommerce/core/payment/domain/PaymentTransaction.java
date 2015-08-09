

package com.wakacommerce.core.payment.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentAdditionalFieldType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.profile.core.domain.AdditionalFields;
import com.wakacommerce.profile.core.domain.Customer;

/**
 *
 * @ hui
 */
public interface PaymentTransaction extends Serializable, Status, AdditionalFields {

    public Long getId();

    public void setId(Long id);

    public OrderPayment getOrderPayment();

    public void setOrderPayment(OrderPayment payment);

    public PaymentTransaction getParentTransaction();

    public void setParentTransaction(PaymentTransaction parentTransaction);

    public PaymentTransactionType getType();

    public void setType(PaymentTransactionType type);

    public Money getAmount();

    public void setAmount(Money amount);

    public Date getDate();

    public void setDate(Date date);

    public String getCustomerIpAddress();

    public void setCustomerIpAddress(String customerIpAddress);

    public String getRawResponse();

    public void setRawResponse(String rawResponse);

    public Boolean getSuccess();

    public void setSuccess(Boolean success);

    public Map<String, String> getAdditionalFields();

    public void setAdditionalFields(Map<String, String> additionalFields);

}
