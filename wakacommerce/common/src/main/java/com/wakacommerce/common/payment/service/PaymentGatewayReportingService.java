

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 * <p>This API provides the ability to get the status of a Transaction after it has been submitted to the Gateway.
 * Gateways have different ways to provide this information.
 * For example, Cybersource can provide a nightly feed or FTP file that contain details of
 * what was SETTLED, CHARGEBACK, etc... to be reconciled in your system.
 * Braintree and Paypal have API hooks to either do a date based query or an individual
 * inquiry on a particular transaction.</p>
 *
 *  
 */
public interface PaymentGatewayReportingService {

    public PaymentResponseDTO findDetailsByTransaction(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
