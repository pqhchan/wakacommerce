

package com.wakacommerce.common.web.payment.processor;

import java.util.Map;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface CreditCardTypesExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType populateCreditCardMap(Map<String, String> creditCardTypes);

}
