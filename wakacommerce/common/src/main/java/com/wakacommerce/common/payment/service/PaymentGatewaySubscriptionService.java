

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface PaymentGatewaySubscriptionService {

    public PaymentResponseDTO createGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO updateGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO cancelGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

}
