

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;

/**
 *
 * @ hui
 */
public interface PaymentGatewayFraudService {

    public PaymentResponseDTO requestPayerAuthentication(PaymentRequestDTO paymentRequestDTO);

}
