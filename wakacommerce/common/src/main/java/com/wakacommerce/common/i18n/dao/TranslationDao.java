

package com.wakacommerce.common.i18n.dao;

import java.util.List;
import java.util.Map;

import com.wakacommerce.common.extension.ResultType;
import com.wakacommerce.common.extension.StandardCacheItem;
import com.wakacommerce.common.i18n.domain.TranslatedEntity;
import com.wakacommerce.common.i18n.domain.Translation;

/**
 *
 * @ hui
 */
public interface TranslationDao {

    public Translation save(Translation translation);

    public Translation create();

    public void delete(Translation translation);

    public Map<String, Object> getIdPropertyMetadata(TranslatedEntity entity);

    Class<?> getEntityImpl(TranslatedEntity entity);

    public Translation readTranslationById(Long translationId);

    public List<Translation> readTranslations(TranslatedEntity entity, String entityId, String fieldName);

    public Translation readTranslation(TranslatedEntity entity, String entityId, String fieldName, String localeCode);

    String getEntityId(TranslatedEntity entityType, Object entity);

    Long countTranslationEntries(TranslatedEntity entityType, ResultType stage);

    List<Translation> readAllTranslationEntries(TranslatedEntity entityType, ResultType stage);

    List<StandardCacheItem> readConvertedTranslationEntries(TranslatedEntity entityType, ResultType stage);

    Translation readTranslation(TranslatedEntity entityType, String entityId, String fieldName, String localeCode, String localeCountryCode, ResultType stage);
}
