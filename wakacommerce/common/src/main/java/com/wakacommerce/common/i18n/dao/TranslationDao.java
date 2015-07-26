

package com.wakacommerce.common.i18n.dao;

import java.util.List;
import java.util.Map;

import com.wakacommerce.common.extension.ResultType;
import com.wakacommerce.common.extension.StandardCacheItem;
import com.wakacommerce.common.i18n.domain.TranslatedEntity;
import com.wakacommerce.common.i18n.domain.Translation;

/**
 * Provides data access for the {@link Translation} entity.
 * 
 *Andre Azzolini (apazzolini)
 */
public interface TranslationDao {

    /**
     * Persists the given translation
     * @param translation
     * @return the saved translation
     */
    public Translation save(Translation translation);
    
    /**
     * Creates an empty translation instance that is not persisted to the database
     * 
     * @return the unsaved, empty translation
     */
    public Translation create();

    /**
     * Deletes the given translation
     * 
     * @param translation
     */
    public void delete(Translation translation);

    /**
     * Returns a map that holds the following data for the given entity:
     *  "name" --> idProperty (the name of the id property, always a String)
     *  "type" --> idProperty's type (usually either Long or String)
     *  
     * @param entity
     * @return the id property's metadata
     */
    public Map<String, Object> getIdPropertyMetadata(TranslatedEntity entity);

    /**
     * Returns the entity implementation class based on the TranslatedEntity
     *
     * @param entity
     * @return the entity implementation class
     */
    Class<?> getEntityImpl(TranslatedEntity entity);
    
    /**
     * Reads a translation by its own primary key
     * 
     * @param translationId
     * @return the translation
     */
    public Translation readTranslationById(Long translationId);
    
    /**
     * Reads all translations for a given field
     * 
     * @param entity
     * @param entityId
     * @param fieldName
     * @return the list of translations
     */
    public List<Translation> readTranslations(TranslatedEntity entity, String entityId, String fieldName);

    /**
     * Reads a translation for the requested parameters. Returns null if there is no translation found
     * 
     * @param entity
     * @param entityId
     * @param fieldName
     * @param localeCode
     * @return the translation
     */
    public Translation readTranslation(TranslatedEntity entity, String entityId, String fieldName, String localeCode);

    /**
     * Get the id for the object. Can take into account hierarchical multitenancy to retrieve the original id.
     *
     * @param entityType
     * @param entity
     * @return
     */
    String getEntityId(TranslatedEntity entityType, Object entity);

    /**
     * Count the number of translations for the given params.
     *
     * @param entityType
     * @param stage param drives whether to look for entries at a template level or standard site level (multitenant concepts). Can be IGNORE. Any multitenant behavior is ignored in the absence of the multitenant module.
     * @return
     */
    Long countTranslationEntries(TranslatedEntity entityType, ResultType stage);

    /**
     * Read all the available translations for the given params.
     *
     * @param entityType
     * @param stage param drives whether to look for entries at a template level or standard site level (multitenant concepts). Can be IGNORE. Any multitenant behavior is ignored in the absence of the multitenant module.
     * @return
     */
    List<Translation> readAllTranslationEntries(TranslatedEntity entityType, ResultType stage);

    /**
     * Read all translation entries (see {@link #readAllTranslationEntries(com.wakacommerce.common.i18n.domain.TranslatedEntity, com.wakacommerce.common.extension.ResultType)}),
     * and convert those results into a list of {@link com.wakacommerce.common.extension.StandardCacheItem} instances.
     *
     * @param entityType
     * @param stage param drives whether to look for entries at a template level or standard site level (multitenant concepts). Can be IGNORE. Any multitenant behavior is ignored in the absence of the multitenant module.
     * @return
     */
    List<StandardCacheItem> readConvertedTranslationEntries(TranslatedEntity entityType, ResultType stage);

    /**
     * Read a specific translation for the given params.
     *
     * @param entityType
     * @param entityId
     * @param fieldName
     * @param localeCode
     * @param localeCountryCode
     * @param stage param drives whether to look for entries at a template level or standard site level (multitenant concepts). Can be IGNORE. Any multitenant behavior is ignored in the absence of the multitenant module.
     * @return
     */
    Translation readTranslation(TranslatedEntity entityType, String entityId, String fieldName, String localeCode, String localeCountryCode, ResultType stage);
}
