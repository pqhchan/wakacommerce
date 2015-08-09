

package com.wakacommerce.common.i18n.service;

import net.sf.ehcache.Cache;

import java.util.List;
import java.util.Locale;

import com.wakacommerce.common.i18n.domain.TranslatedEntity;
import com.wakacommerce.common.i18n.domain.Translation;

public interface TranslationService {

    public Translation save(Translation translation);

    public Translation save(String entityType, String entityId, String fieldName, String localeCode, 
            String translatedValue);

    public Translation update(Long translationId, String localeCode, String translatedValue);

    public void deleteTranslationById(Long translationId);

    public List<Translation> getTranslations(String ceilingEntityClassname, String entityId, String property);

    public Translation getTranslation(TranslatedEntity entity, String entityId, String fieldName, String localeCode);

    public String getTranslatedValue(Object entity, String property, Locale locale);

    void removeTranslationFromCache(Translation translation);

    Translation findTranslationById(Long id);

    Cache getCache();

}
