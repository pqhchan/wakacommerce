package com.wakacommerce.cms.structure.service;

import net.sf.ehcache.Cache;

import org.hibernate.Criteria;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.cms.structure.domain.StructuredContentType;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;

import java.util.List;
import java.util.Map;

public interface StructuredContentService {

    StructuredContent findStructuredContentById(Long contentId);

    StructuredContentType findStructuredContentTypeById(Long id);

    StructuredContentType findStructuredContentTypeByName(String name);

    List<StructuredContentType> retrieveAllStructuredContentTypes();

    List<StructuredContent> findContentItems(Criteria criteria);

    List<StructuredContent> findAllContentItems();

    Long countContentItems(Criteria c);

    StructuredContentType saveStructuredContentType(StructuredContentType type);

    /**
     * This method returns active content items for the passed in sandbox that match the passed in type.

     * @see com.wakacommerce.cms.web.structure.DisplayContentTag
     */
    List<StructuredContentDTO> lookupStructuredContentItemsByType(StructuredContentType contentType, Integer count, Map<String,Object> ruleDTOs, boolean secure);

    List<StructuredContentDTO> lookupStructuredContentItemsByName(String contentName, Integer count, Map<String,Object> ruleDTOs, boolean secure);

    List<StructuredContentDTO> lookupStructuredContentItemsByName(StructuredContentType contentType, String contentName, Integer count, Map<String,Object> ruleDTOs, boolean secure);

    Locale findLanguageOnlyLocale(Locale locale);

    List<StructuredContentDTO> buildStructuredContentDTOList(List<StructuredContent> structuredContentList, boolean secure);

    List<StructuredContentDTO> evaluateAndPriortizeContent(List<StructuredContentDTO> structuredContentList, int count, Map<String, Object> ruleDTOs);

    void removeStructuredContentFromCache(SandBox sandBox, StructuredContent sc);

    Cache getStructuredContentCache();

    StructuredContentDTO buildStructuredContentDTO(StructuredContent sc, boolean secure);


    public void addStructuredContentListToCache(String key, List<StructuredContentDTO> scDTOList);


    public String buildTypeKey(SandBox currentSandbox, Long site, String contentType);


    public List<StructuredContentDTO> getStructuredContentListFromCache(String key);

    public void removeItemFromCache(String nameKey, String typeKey);

    public List<StructuredContentDTO> convertToDtos(List<StructuredContent> scs, boolean isSecure);

}
