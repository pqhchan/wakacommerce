

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface PaymentGatewayCreditCardService {

    public PaymentResponseDTO createGatewayCreditCard(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO updateGatewayCreditCard(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO deleteGatewayCreditCard(PaymentRequestDTO requestDTO) throws PaymentException;

}
