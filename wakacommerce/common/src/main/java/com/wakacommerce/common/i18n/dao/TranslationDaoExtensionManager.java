
package com.wakacommerce.common.i18n.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blTranslationDaoExtensionManager")
public class TranslationDaoExtensionManager extends ExtensionManager<TranslationDaoExtensionHandler> {

    public TranslationDaoExtensionManager() {
        super(TranslationDaoExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return true;
    }
}
