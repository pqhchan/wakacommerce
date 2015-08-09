

package com.wakacommerce.core.payment.service;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.FulfillmentGroupService;
import com.wakacommerce.core.payment.domain.PaymentTransaction;

/**
 *
 * @ hui
 */
public interface OrderToPaymentRequestDTOService {

    public PaymentRequestDTO translateOrder(Order order);

    public PaymentRequestDTO translatePaymentTransaction(Money transactionAmount, PaymentTransaction paymentTransaction);
    
    public void populateTotals(Order order, PaymentRequestDTO requestDTO);
    
    public void populateCustomerInfo(Order order, PaymentRequestDTO requestDTO);

    public void populateShipTo(Order order, PaymentRequestDTO requestDTO);
    
    public void populateBillTo(Order order, PaymentRequestDTO requestDTO);

    public void populateDefaultLineItemsAndSubtotal(Order order, PaymentRequestDTO requestDTO);
}
