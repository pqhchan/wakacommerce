

package com.wakacommerce.common.web.payment.expression;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *  
 */
@Service("blPaymentGatewayFieldExtensionManager")
public class PaymentGatewayFieldExtensionManager extends ExtensionManager<PaymentGatewayFieldExtensionHandler> {

    public PaymentGatewayFieldExtensionManager() {
        super(PaymentGatewayFieldExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
