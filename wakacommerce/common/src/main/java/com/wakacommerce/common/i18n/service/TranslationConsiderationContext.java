
package com.wakacommerce.common.i18n.service;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;

/**
 *
 * @ hui
 */
public class TranslationConsiderationContext {

    private static final ThreadLocal<TranslationConsiderationContext> translationConsiderationContext = ThreadLocalManager.createThreadLocal(TranslationConsiderationContext.class);
    
    public static boolean hasTranslation() {
        return getTranslationConsiderationContext() != null && getTranslationConsiderationContext() && getTranslationService() != null;
    }
    
    public static Boolean getTranslationConsiderationContext() {
        Boolean val = TranslationConsiderationContext.translationConsiderationContext.get().enabled;
        return val == null ? false : val;
    }
    
    public static void setTranslationConsiderationContext(Boolean isEnabled) {
        TranslationConsiderationContext.translationConsiderationContext.get().enabled = isEnabled;
    }
    
    public static TranslationService getTranslationService() {
        return TranslationConsiderationContext.translationConsiderationContext.get().service;
    }
    
    public static void setTranslationService(TranslationService translationService) {
        TranslationConsiderationContext.translationConsiderationContext.get().service = translationService;
    }

    protected Boolean enabled = false;
    protected TranslationService service;
}
