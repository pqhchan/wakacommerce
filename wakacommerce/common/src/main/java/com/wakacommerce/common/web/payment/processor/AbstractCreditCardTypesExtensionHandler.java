
package com.wakacommerce.common.web.payment.processor;

import java.util.Map;
import javax.annotation.Resource;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.service.PaymentGatewayResolver;

/**
 *
 * @ hui
 */
public abstract class AbstractCreditCardTypesExtensionHandler extends AbstractExtensionHandler
        implements CreditCardTypesExtensionHandler {

    @Resource(name = "blPaymentGatewayResolver")
    protected PaymentGatewayResolver paymentGatewayResolver;

    @Override
    public ExtensionResultStatusType populateCreditCardMap(Map<String, String> creditCardTypes) {

        if (paymentGatewayResolver.isHandlerCompatible(getHandlerType())) {
            setCardTypes(creditCardTypes);
            return ExtensionResultStatusType.HANDLED;
        }

        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public abstract PaymentGatewayType getHandlerType();

    public abstract void setCardTypes(Map<String, String> creditCardTypes);

}
