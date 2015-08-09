
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.money.util.CurrencyAdapter;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.util.xml.BigDecimalRoundingAdapter;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.payment.service.OrderPaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "payment")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderPaymentWrapper extends BaseWrapper implements APIWrapper<OrderPayment>, APIUnwrapper<OrderPayment> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long orderId;

    @XmlElement
    protected String type;

    @XmlElement
    protected AddressWrapper billingAddress;

    @XmlElement
    @XmlJavaTypeAdapter(value = BigDecimalRoundingAdapter.class)
    protected BigDecimal amount;

    @XmlElement
    @XmlJavaTypeAdapter(value = CurrencyAdapter.class)
    protected Currency currency;

    @XmlElement
    protected String referenceNumber;

    @XmlElement
    protected String gatewayType;

    @XmlElement(name = "transaction")
    @XmlElementWrapper(name = "transactions")
    protected List<PaymentTransactionWrapper> transactions;

    @Override
    public void wrapDetails(OrderPayment model, HttpServletRequest request) {
        this.id = model.getId();

        if (model.getOrder() != null) {
            this.orderId = model.getOrder().getId();
        }

        if (model.getType() != null) {
            this.type = model.getType().getType();
        }

        if (model.getGatewayType() != null) {
            this.gatewayType = model.getGatewayType().getType();
        }

        if (model.getBillingAddress() != null) {
            AddressWrapper addressWrapper = (AddressWrapper) context.getBean(AddressWrapper.class.getName());
            addressWrapper.wrapDetails(model.getBillingAddress(), request);
            this.billingAddress = addressWrapper;
        }

        if (model.getAmount() != null) {
            this.amount = model.getAmount().getAmount();
            this.currency = model.getAmount().getCurrency();
        }

        if (model.getTransactions() != null && !model.getTransactions().isEmpty()) {
            this.transactions = new ArrayList<PaymentTransactionWrapper>();
            for (PaymentTransaction transaction : model.getTransactions()) {
                PaymentTransactionWrapper transactionWrapper = (PaymentTransactionWrapper) context.getBean(PaymentTransactionWrapper.class.getName());
                transactionWrapper.wrapSummary(transaction, request);
                this.transactions.add(transactionWrapper);
            }
        }

        this.referenceNumber = model.getReferenceNumber();
    }

    @Override
    public void wrapSummary(OrderPayment model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public OrderPayment unwrap(HttpServletRequest request, ApplicationContext context) {
        OrderPaymentService orderPaymentService = (OrderPaymentService) context.getBean("blOrderPaymentService");
        OrderPayment payment = orderPaymentService.create();

        OrderService orderService = (OrderService) context.getBean("blOrderService");
        Order order = orderService.findOrderById(this.orderId);
        if (order != null) {
            payment.setOrder(order);
        }
        
        payment.setId(this.id);
        payment.setType(PaymentType.getInstance(this.type));
        payment.setPaymentGatewayType(PaymentGatewayType.getInstance(this.gatewayType));
        payment.setReferenceNumber(this.referenceNumber);

        if (this.billingAddress != null) {
            payment.setBillingAddress(this.billingAddress.unwrap(request, context));
        }

        if (this.amount != null) {
            if (this.currency != null) {
                payment.setAmount(new Money(this.amount, this.currency));
            } else {
                payment.setAmount(new Money(this.amount));
            }
        }

        if (this.transactions != null && !this.transactions.isEmpty()) {
            for (PaymentTransactionWrapper transactionWrapper : this.transactions) {
                PaymentTransaction transaction = transactionWrapper.unwrap(request,context);
                transaction.setOrderPayment(payment);
                payment.addTransaction(transaction);
            }
        }

        return payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddressWrapper getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressWrapper billingAddress) {
        this.billingAddress = billingAddress;
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public List<PaymentTransactionWrapper> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<PaymentTransactionWrapper> transactions) {
        this.transactions = transactions;
    }
}
