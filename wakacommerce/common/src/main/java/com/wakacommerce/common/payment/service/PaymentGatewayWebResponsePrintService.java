

package com.wakacommerce.common.payment.service;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface PaymentGatewayWebResponsePrintService {

    public String printRequest(HttpServletRequest request);

}
