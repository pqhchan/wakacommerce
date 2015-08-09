

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface PaymentGatewayReportingService {

    public PaymentResponseDTO findDetailsByTransaction(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
