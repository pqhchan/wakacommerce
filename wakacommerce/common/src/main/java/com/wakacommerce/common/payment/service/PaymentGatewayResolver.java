
package com.wakacommerce.common.payment.service;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 *
 * @ hui
 */
public interface PaymentGatewayResolver {

    public boolean isHandlerCompatible(PaymentGatewayType handlerType);

    public PaymentGatewayType resolvePaymentGateway(WebRequest request);

}
