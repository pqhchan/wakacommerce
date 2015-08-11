package com.wakacommerce.common.payment.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;

/**
 *
 * @ hui
 */
public class PaymentResponseDTO {

    protected GatewayCustomerDTO<PaymentResponseDTO> customer;

    protected AddressDTO<PaymentResponseDTO> shipTo;

    protected AddressDTO<PaymentResponseDTO> billTo;

    protected CreditCardDTO<PaymentResponseDTO> creditCard;

    protected List<GiftCardDTO<PaymentResponseDTO>> giftCards;

    protected List<CustomerCreditDTO<PaymentResponseDTO>> customerCredits;

    protected PaymentGatewayType paymentGatewayType;

    protected PaymentType paymentType;

    protected PaymentTransactionType paymentTransactionType;

    protected String orderId;

    protected Money amount;

    protected boolean successful = true;

    protected boolean valid = true;

    protected boolean completeCheckoutOnCallback = true;

    protected String rawResponse;

    protected Map<String, String> responseMap;

    public PaymentResponseDTO(PaymentType paymentType, PaymentGatewayType gatewayType) {
        this.paymentType = paymentType;
        this.paymentGatewayType = gatewayType;
        this.giftCards = new ArrayList<GiftCardDTO<PaymentResponseDTO>>();
        this.customerCredits = new ArrayList<CustomerCreditDTO<PaymentResponseDTO>>();
        this.responseMap = new HashMap<String, String>();
    }

    public GatewayCustomerDTO<PaymentResponseDTO> customer() {
        customer = new GatewayCustomerDTO<PaymentResponseDTO>(this);
        return customer;
    }

    public CreditCardDTO<PaymentResponseDTO> creditCard() {
        creditCard = new CreditCardDTO<PaymentResponseDTO>(this);
        return creditCard;
    }

    public AddressDTO<PaymentResponseDTO> shipTo() {
        shipTo = new AddressDTO<PaymentResponseDTO>(this);
        return shipTo;
    }

    public AddressDTO<PaymentResponseDTO> billTo() {
        billTo = new AddressDTO<PaymentResponseDTO>(this);
        return billTo;
    }

    public GiftCardDTO<PaymentResponseDTO> giftCard() {
        GiftCardDTO<PaymentResponseDTO> giftCardDTO = new GiftCardDTO<PaymentResponseDTO>(this);
        giftCards.add(giftCardDTO);
        return giftCardDTO;
    }

    public CustomerCreditDTO<PaymentResponseDTO> customerCredit() {
        CustomerCreditDTO<PaymentResponseDTO> customerCreditDTO = new CustomerCreditDTO<PaymentResponseDTO>(this);
        customerCredits.add(customerCreditDTO);
        return customerCreditDTO;
    }

    public PaymentResponseDTO responseMap(String key, String value) {
        responseMap.put(key, value);
        return this;
    }

    public PaymentResponseDTO orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentResponseDTO amount(Money amount) {
        this.amount = amount;
        return this;
    }

    public PaymentResponseDTO paymentTransactionType(PaymentTransactionType paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType;
        return this;
    }

    public PaymentResponseDTO successful(boolean successful) {
        this.successful = successful;
        return this;
    }

    public PaymentResponseDTO completeCheckoutOnCallback(boolean completeCheckoutOnCallback) {
        this.completeCheckoutOnCallback = completeCheckoutOnCallback;
        return this;
    }

    public PaymentResponseDTO valid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public PaymentResponseDTO rawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        return this;
    }

    public GatewayCustomerDTO<PaymentResponseDTO> getCustomer() {
        return customer;
    }

    public AddressDTO<PaymentResponseDTO> getShipTo() {
        return shipTo;
    }

    public AddressDTO<PaymentResponseDTO> getBillTo() {
        return billTo;
    }

    public List<GiftCardDTO<PaymentResponseDTO>> getGiftCards() {
        return giftCards;
    }

    public List<CustomerCreditDTO<PaymentResponseDTO>> getCustomerCredits() {
        return customerCredits;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PaymentGatewayType getPaymentGatewayType() {
        return paymentGatewayType;
    }

    public String getOrderId() {
        return orderId;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentTransactionType getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isCompleteCheckoutOnCallback() {
        return completeCheckoutOnCallback;
    }

    public CreditCardDTO<PaymentResponseDTO> getCreditCard() {
        return creditCard;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public Map<String, String> getResponseMap() {
        return responseMap;
    }
}
