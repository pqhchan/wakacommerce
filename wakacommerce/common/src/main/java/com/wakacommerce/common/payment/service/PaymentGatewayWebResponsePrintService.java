

package com.wakacommerce.common.payment.service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>This is a utility service that aids in translating the Request Attribute and
 * Request Parameters to a single String. This is useful when setting the Raw Response
 * fields on a PaymentResponseDTO. Primarily used in the PaymentGatewayWebResponseService
 * but can be injected anywhere you need to get the attributes or paraeters from an HTTPServletRequest
 * as a String.</p>
 *
 * @see {@link PaymentGatewayWebResponseService}
 * @see {@link com.wakacommerce.common.payment.dto.PaymentResponseDTO}
 *
 *  
 */
public interface PaymentGatewayWebResponsePrintService {

    public String printRequest(HttpServletRequest request);

}
