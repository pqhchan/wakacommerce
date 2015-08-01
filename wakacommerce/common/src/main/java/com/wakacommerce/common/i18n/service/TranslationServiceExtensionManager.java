
package com.wakacommerce.common.i18n.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.extension.SparselyPopulatedQueryExtensionHandler;


/**
 * Provide specialized cache population queries for price lists. This follows the sparsely populated cache
 * pattern for multitenancy.
 *
 * @see com.wakacommerce.common.extension.SparselyPopulatedQueryExtensionHandler
 * 
 */
@Service("blTranslationServiceExtensionManager")
public class TranslationServiceExtensionManager extends ExtensionManager<SparselyPopulatedQueryExtensionHandler> {

    public TranslationServiceExtensionManager() {
        super(SparselyPopulatedQueryExtensionHandler.class);
    }

}
