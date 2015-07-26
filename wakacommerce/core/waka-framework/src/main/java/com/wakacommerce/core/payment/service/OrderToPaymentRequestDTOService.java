

package com.wakacommerce.core.payment.service;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.FulfillmentGroupService;
import com.wakacommerce.core.payment.domain.PaymentTransaction;

/**
 *Elbert Bautista (elbertbautista)
 */
public interface OrderToPaymentRequestDTOService {

    /**
     * <p>This translates an Order of {@link PaymentType#CREDIT_CARD} into a PaymentRequestDTO.
     * This method assumes that this translation will apply to a final payment which means that the transaction amount for
     * the returned {@link PaymentRequestDTO} will be {@link Order#getTotalAfterAppliedPayments()}
     * It assumes that all other payments (e.g. gift cards/account credit) have already
     * been applied to the {@link Order}.</p>
     *
     * @param order the {@link Order} to be translated
     * @return a {@link PaymentRequestDTO} based on the properties of <b>order</b>. This will only utilize the payments
     * that are of type {@link PaymentType#CREDIT_CARD}
     */
    public PaymentRequestDTO translateOrder(Order order);

    /**
     * Utilizes the {@link PaymentTransaction#getAdditionalFields()} map to populate necessary request parameters on the
     * resulting {@link PaymentRequestDTO}. These additional fields are then used by the payment gateway to construct
     * additional requests to the payment gateway. For instance, this might be use to refund or void the given <b>paymentTransaction</b>
     * 
     * @param transactionAmount the amount that should be placed on {@link PaymentRequestDTO#getTransactionTotal()}
     * @param paymentTransaction the transaction whose additional fields should be placed on {@link PaymentRequestDTO#getAdditionalFields()}
     * for the gateway to use
     * @return a new {@link PaymentRequestDTO} populated with the additional fields from <b>paymentTransaction</b> and
     * the amount from <b>transactionAmount<b>
     */
    public PaymentRequestDTO translatePaymentTransaction(Money transactionAmount, PaymentTransaction paymentTransaction);
    
    public void populateTotals(Order order, PaymentRequestDTO requestDTO);
    
    public void populateCustomerInfo(Order order, PaymentRequestDTO requestDTO);
    
    /**
     * Uses the first shippable fulfillment group to populate the {@link PaymentRequestDTO#shipTo()} object
     * @param order the {@link Order} to get data from
     * @param requestDTO the {@link PaymentRequestDTO} that should be populated
     * @see {@link FulfillmentGroupService#getFirstShippableFulfillmentGroup(Order)}
     */
    public void populateShipTo(Order order, PaymentRequestDTO requestDTO);
    
    public void populateBillTo(Order order, PaymentRequestDTO requestDTO);

    public void populateDefaultLineItemsAndSubtotal(Order order, PaymentRequestDTO requestDTO);
}
