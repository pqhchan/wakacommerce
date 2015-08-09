

package com.wakacommerce.common.web.payment.processor;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blTRCreditCardExtensionManager")
public class TRCreditCardExtensionManager extends ExtensionManager<TRCreditCardExtensionHandler> {

    public TRCreditCardExtensionManager() {
        super(TRCreditCardExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}