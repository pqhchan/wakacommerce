

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 * <p>Some gateways allow you to create a form of recurring billing by creating a subscription profile.
 * Note: Some Gateways charge an extra fee to enable this feature</p>
 *
 *Elbert Bautista (elbertbautista)
 */
public interface PaymentGatewaySubscriptionService {

    public PaymentResponseDTO createGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO updateGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO cancelGatewaySubscription(PaymentRequestDTO requestDTO) throws PaymentException;

}
