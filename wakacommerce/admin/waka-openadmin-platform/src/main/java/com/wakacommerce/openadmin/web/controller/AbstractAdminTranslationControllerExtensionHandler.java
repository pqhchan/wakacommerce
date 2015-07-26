
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.form.TranslationForm;

/**
 * 
 *Andre Azzolini (apazzolini)
 */
public abstract class AbstractAdminTranslationControllerExtensionHandler extends AbstractExtensionHandler
        implements AdminTranslationControllerExtensionHandler {

    @Override
    public ExtensionResultStatusType applyTransformation(TranslationForm form) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
