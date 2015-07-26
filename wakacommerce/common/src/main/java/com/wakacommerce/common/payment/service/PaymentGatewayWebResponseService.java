

package com.wakacommerce.common.payment.service;

import javax.servlet.http.HttpServletRequest;

import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 * <p>The purpose of this class, is to provide an API that will translate a web response
 * returned from a Payment Gateway into a PaymentResponseDTO</p>
 *
 * <p>Some payment gateways provide the ability that ensures that the transaction data
 * is passed back to your application when a transaction is completed.
 * Most of the gateways issue an HTML Post to return data to your server for both
 * approved and declined transactions. This occurs even if a customer closes the browser
 * before returning to your site, or if the payment response is somehow severed.</p>
 *
 * <p>Many gateways will continue calling your exposed API Webhook for a certain period until
 * a 200 Response is received. Others will forward to an error page configured through the gateway.</p>
 *
 * <p>This is usually invoked by a gateway endpoint controller that extends PaymentGatewayAbstractController</p>
 *
 * @see {@link com.wakacommerce.common.web.payment.controller.PaymentGatewayAbstractController}
 *
 *Elbert Bautista (elbertbautista)
 */
public interface PaymentGatewayWebResponseService {

    public PaymentResponseDTO translateWebResponse(HttpServletRequest request) throws PaymentException;

}
