
package com.wakacommerce.common.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 * Default Resolver implementation. Extensions and modules can override this to provide
 * more exotic scenarios on which PaymentGateway should be used.
 *
 *  
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
