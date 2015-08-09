

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.web.payment.controller.PaymentGatewayAbstractController;

/**
 *
 * @ hui
 */
public interface PaymentGatewayCheckoutService {

    public Long applyPaymentToOrder(PaymentResponseDTO responseDTO, PaymentGatewayConfiguration config)
        throws IllegalArgumentException;

    public void markPaymentAsInvalid(Long orderPaymentId);

    public String initiateCheckout(Long orderId) throws Exception;

    public String lookupOrderNumberFromOrderId(PaymentResponseDTO responseDTO) throws IllegalArgumentException;

}
