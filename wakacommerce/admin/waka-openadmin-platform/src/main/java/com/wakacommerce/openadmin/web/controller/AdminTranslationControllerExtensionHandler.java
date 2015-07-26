
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.form.TranslationForm;

/**
 *Jeff Fischer
 */
public interface AdminTranslationControllerExtensionHandler extends ExtensionHandler {

    /**
     * Applies any necessary transformations to the given form. For example, some entity fields might need to be
     * mapped in a different way.
     *
     * @param form
     */
    public ExtensionResultStatusType applyTransformation(TranslationForm form);

}
