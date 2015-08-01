

package com.wakacommerce.common.web.payment.processor;

import java.util.Map;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *  
 */
public interface CreditCardTypesExtensionHandler extends ExtensionHandler {

    /**
     * The registered Extension Handler will populate any specific Payment Gateway
     * codes required for Credit Card Types.
     *
     * key = "Card Type Code to send to the Gateway"
     * value = "Friendly Name of Card type (e.g. Visa, MasterCard, etc...)"
     *
     * @param creditCardTypes
     * @return
     */
    public ExtensionResultStatusType populateCreditCardMap(Map<String, String> creditCardTypes);

}
