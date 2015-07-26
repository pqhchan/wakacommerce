

package com.wakacommerce.common.web.payment.processor;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Elbert Bautista (elbertbautista)
 */
@Service("blCreditCardTypesExtensionManager")
public class CreditCardTypesExtensionManager extends ExtensionManager<CreditCardTypesExtensionHandler> {

    public CreditCardTypesExtensionManager() {
        super(CreditCardTypesExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
