package com.wakacommerce.cms.structure.dao;

import java.util.List;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.cms.structure.domain.StructuredContentType;

public interface StructuredContentDao {

	/**
	 * @param contentId StructuredContent的Id值
	 * @return id值等于contentId的StructuredContent或null, 如果不存在的话
	 */
    public StructuredContent findStructuredContentById(Long contentId);

    public List<StructuredContent> findAllContentItems();

    public StructuredContent addOrUpdateContentItem(StructuredContent content);

    public void delete(StructuredContent content);

    public StructuredContentType findStructuredContentTypeById(Long contentTypeId);

    public List<StructuredContentType> retrieveAllStructuredContentTypes();

    public StructuredContentType saveStructuredContentType(StructuredContentType type);

    public List<StructuredContent> findActiveStructuredContentByType(StructuredContentType type);

    public List<StructuredContent> findActiveStructuredContentByNameAndType(StructuredContentType type, String name);

    public List<StructuredContent> findActiveStructuredContentByName(String name);

    public StructuredContentType findStructuredContentTypeByName(String name);

    public void detach(StructuredContent sc);
}
