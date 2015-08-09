

package com.wakacommerce.common.payment.service;

import javax.servlet.http.HttpServletRequest;

import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface PaymentGatewayWebResponseService {

    public PaymentResponseDTO translateWebResponse(HttpServletRequest request) throws PaymentException;

}
