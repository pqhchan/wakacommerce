

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface PaymentGatewayTransactionService {

    public PaymentResponseDTO authorize(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO capture(PaymentRequestDTO paymentRequestDTO) throws PaymentException ;

    public PaymentResponseDTO authorizeAndCapture(PaymentRequestDTO paymentRequestDTO) throws PaymentException ;

    public PaymentResponseDTO reverseAuthorize(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO refund(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO voidPayment(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
