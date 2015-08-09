
package com.wakacommerce.common.i18n.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.extension.SparselyPopulatedQueryExtensionHandler;


/**
 *
 * @ hui
 */
@Service("blTranslationServiceExtensionManager")
public class TranslationServiceExtensionManager extends ExtensionManager<SparselyPopulatedQueryExtensionHandler> {

    public TranslationServiceExtensionManager() {
        super(SparselyPopulatedQueryExtensionHandler.class);
    }

}
