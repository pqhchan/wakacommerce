
package com.wakacommerce.common.i18n.service;

import org.apache.commons.lang3.StringUtils;

import com.wakacommerce.common.web.WakaRequestContext;

import java.util.Locale;

/**
 *
 * @ hui
 */
public class DynamicTranslationProvider {

    public static String getValue(Object obj, String field, final String defaultValue) {
        String valueToReturn = defaultValue;
        
        if (TranslationConsiderationContext.hasTranslation()) {
            TranslationService translationService = TranslationConsiderationContext.getTranslationService();
            Locale locale = WakaRequestContext.getWakaRequestContext().getJavaLocale();
            String translatedValue = translationService.getTranslatedValue(obj, field, locale);
            
            if (StringUtils.isNotBlank(translatedValue)) {
                valueToReturn = translatedValue;
            }
        }
            
        return valueToReturn;
    }

}
