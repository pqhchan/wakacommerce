
package com.wakacommerce.common.payment.service;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 *Elbert Bautista (elbertbautista)
 */
public interface PaymentGatewayResolver {

    /**
     * Used by Transparent Redirect Solutions that utilize Thymeleaf Processors and Expressions.
     * This method should determine whether or not an extension handler should run for a particular gateway.
     * @param handlerType
     * @return
     */
    public boolean isHandlerCompatible(PaymentGatewayType handlerType);

    /**
     * Resolves a {@link com.wakacommerce.common.payment.PaymentGatewayType}
     * based on a {@link org.springframework.web.context.request.WebRequest}
     * @param request
     * @return
     */
    public PaymentGatewayType resolvePaymentGateway(WebRequest request);

}
