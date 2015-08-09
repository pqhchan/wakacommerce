
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.payment.domain.secure.BankAccountPayment;
import com.wakacommerce.core.payment.domain.secure.CreditCardPayment;
import com.wakacommerce.core.payment.domain.secure.GiftCardPayment;
import com.wakacommerce.core.payment.domain.secure.Referenced;
import com.wakacommerce.core.payment.service.SecureOrderPaymentService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "referenced")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ReferencedWrapper extends BaseWrapper implements APIWrapper<Referenced>, APIUnwrapper<Referenced>{

    @XmlElement
    protected Long id;

    @XmlElement
    protected String referenceNumber;

    @XmlElement
    protected String type;

    @XmlElement
    protected String pan;

    @XmlElement
    protected String cvvCode;

    @XmlElement
    protected Integer expirationMonth;

    @XmlElement
    protected Integer expirationYear;

    @XmlElement
    protected String accountNumber;

    @XmlElement
    protected String routingNumber;

    @XmlElement
    protected String pin;

    @Override
    public void wrapDetails(Referenced model, HttpServletRequest request) {
        this.id = model.getId();
        this.referenceNumber = model.getReferenceNumber();

        if (model instanceof CreditCardPayment) {
            CreditCardPayment referenced = (CreditCardPayment) model;
            this.type = CreditCardPayment.class.getName();

            this.pan = referenced.getPan();
            this.cvvCode = referenced.getCvvCode();
            this.expirationMonth = referenced.getExpirationMonth();
            this.expirationYear = referenced.getExpirationYear();
        }

        if (model instanceof BankAccountPayment) {
            BankAccountPayment referenced = (BankAccountPayment) model;
            this.type = BankAccountPayment.class.getName();

            this.accountNumber = referenced.getAccountNumber();
            this.routingNumber = referenced.getRoutingNumber();
        }

        if (model instanceof GiftCardPayment) {
            GiftCardPayment referenced = (GiftCardPayment) model;
            this.type = GiftCardPayment.class.getName();

            this.pan = referenced.getPan();
            this.pin = referenced.getPin();
        }

    }

    @Override
    public Referenced unwrap(HttpServletRequest request, ApplicationContext context) {
        SecureOrderPaymentService securePaymentInfoService = (SecureOrderPaymentService) context.getBean("blSecureOrderPaymentService");

        if (CreditCardPayment.class.getName().equals(this.type)) {
            CreditCardPayment paymentInfo = (CreditCardPayment) securePaymentInfoService.create(PaymentType.CREDIT_CARD);
            paymentInfo.setId(this.id);
            paymentInfo.setReferenceNumber(this.referenceNumber);
            paymentInfo.setPan(this.pan);
            paymentInfo.setCvvCode(this.cvvCode);
            paymentInfo.setExpirationMonth(this.expirationMonth);
            paymentInfo.setExpirationYear(this.expirationYear);

            return paymentInfo;
        }

        if (BankAccountPayment.class.getName().equals(this.type)) {
            BankAccountPayment paymentInfo = (BankAccountPayment) securePaymentInfoService.create(PaymentType.BANK_ACCOUNT);
            paymentInfo.setId(this.id);
            paymentInfo.setReferenceNumber(this.referenceNumber);
            paymentInfo.setAccountNumber(this.accountNumber);
            paymentInfo.setRoutingNumber(this.routingNumber);

            return paymentInfo;
        }

        if (GiftCardPayment.class.getName().equals(this.type)) {
            GiftCardPayment paymentInfo = (GiftCardPayment) securePaymentInfoService.create(PaymentType.GIFT_CARD);
            paymentInfo.setId(this.id);
            paymentInfo.setReferenceNumber(this.referenceNumber);
            paymentInfo.setPan(this.pan);
            paymentInfo.setPin(this.pin);

            return paymentInfo;
        }

        return null;
    }

    @Override
    public void wrapSummary(Referenced model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
