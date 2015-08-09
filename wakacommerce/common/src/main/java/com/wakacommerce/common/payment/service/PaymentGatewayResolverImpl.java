
package com.wakacommerce.common.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 *
 * @ hui
 */
@Service("blPaymentGatewayResolver")
public class PaymentGatewayResolverImpl implements PaymentGatewayResolver {

    @Override
    public boolean isHandlerCompatible(PaymentGatewayType handlerType) {
        return true;
    }

    @Override
    public PaymentGatewayType resolvePaymentGateway(WebRequest request) {
        return null;
    }

}
