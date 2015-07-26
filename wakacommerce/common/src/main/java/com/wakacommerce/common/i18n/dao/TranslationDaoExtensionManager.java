
package com.wakacommerce.common.i18n.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *Andre Azzolini (apazzolini)
 */
@Service("blTranslationDaoExtensionManager")
public class TranslationDaoExtensionManager extends ExtensionManager<TranslationDaoExtensionHandler> {

    public TranslationDaoExtensionManager() {
        super(TranslationDaoExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    public boolean continueOnHandled() {
        return true;
    }
}
