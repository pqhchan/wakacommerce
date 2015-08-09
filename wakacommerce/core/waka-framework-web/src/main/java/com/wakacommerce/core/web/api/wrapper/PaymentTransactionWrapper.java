

package com.wakacommerce.core.web.api.wrapper;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.money.util.CurrencyAdapter;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.util.xml.BigDecimalRoundingAdapter;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.payment.service.OrderPaymentService;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "transaction")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PaymentTransactionWrapper extends BaseWrapper implements APIWrapper<PaymentTransaction>, APIUnwrapper<PaymentTransaction> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long orderPaymentId;

    @XmlElement
    protected Long parentTransactionId;

    @XmlElement
    protected String type;

    @XmlElement
    protected String customerIpAddress;

    @XmlElement
    protected String rawResponse;

    @XmlElement
    protected Boolean success;

    @XmlElement
    @XmlJavaTypeAdapter(value = BigDecimalRoundingAdapter.class)
    protected BigDecimal amount;

    @XmlElement
    @XmlJavaTypeAdapter(value = CurrencyAdapter.class)
    protected Currency currency;

    @XmlElement(name = "element")
    @XmlElementWrapper(name = "additionalFields")
    protected List<MapElementWrapper> additionalFields;

    @Override
    public void wrapDetails(PaymentTransaction model, HttpServletRequest request) {
        this.id = model.getId();

        if (model.getOrderPayment() != null) {
            this.orderPaymentId = model.getOrderPayment().getId();
        }

        if (model.getParentTransaction() != null) {
            this.parentTransactionId = model.getParentTransaction().getId();
        }

        if (model.getType() != null) {
            this.type = model.getType().getType();
        }

        this.customerIpAddress = model.getCustomerIpAddress();
        this.rawResponse = model.getRawResponse();
        this.success = model.getSuccess();

        if (model.getAmount() != null) {
            this.amount = model.getAmount().getAmount();
            this.currency = model.getAmount().getCurrency();
        }

        this.additionalFields = super.createElementWrappers(model);

    }

    @Override
    public void wrapSummary(PaymentTransaction model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public PaymentTransaction unwrap(HttpServletRequest request, ApplicationContext context) {
        OrderPaymentService orderPaymentService = (OrderPaymentService) context.getBean("blOrderPaymentService");
        PaymentTransaction transaction = orderPaymentService.createTransaction();

        if (this.parentTransactionId != null) {
            PaymentTransaction parentTransaction = orderPaymentService.readTransactionById(this.parentTransactionId);
            transaction.setParentTransaction(parentTransaction);
        }

        transaction.setType(PaymentTransactionType.getInstance(this.type));

        if (this.additionalFields != null && !this.additionalFields.isEmpty()) {
            Map<String, String> fields = new HashMap<String, String>();
            for (MapElementWrapper mapElementWrapper : this.additionalFields) {
                fields.put(mapElementWrapper.getKey(), mapElementWrapper.getValue());
            }

            transaction.setAdditionalFields(fields);
        }

        if (this.amount != null) {
            if (this.currency != null) {
                transaction.setAmount(new Money(this.amount, this.currency));
            } else {
                transaction.setAmount(new Money(this.amount));
            }
        }

        transaction.setCustomerIpAddress(this.customerIpAddress);
        transaction.setRawResponse(this.rawResponse);
        transaction.setSuccess(this.success);

        return transaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(Long orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public Long getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(Long parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public void setCustomerIpAddress(String customerIpAddress) {
        this.customerIpAddress = customerIpAddress;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<MapElementWrapper> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(List<MapElementWrapper> additionalFields) {
        this.additionalFields = additionalFields;
    }
}
