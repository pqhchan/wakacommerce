package com.wakacommerce.cms.structure.dao;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.cms.structure.domain.StructuredContentType;
import com.wakacommerce.common.locale.domain.Locale;

import java.util.List;

public interface StructuredContentDao {

	/**
	 * @param contentId StructuredContent的Id值
	 * @return id值等于contentId的StructuredContent或null, 如果不存在的话
	 */
    public StructuredContent findStructuredContentById(Long contentId);

    /**
     * 不考虑StructuredContent所属的Sandbox
     * 
     * @return 系统中所有的StructuredContent; 特定于具体的持久化组件, 
     * 如果没有数据的话, 返回的可能是null, 也可能是空列表. 
     */
    public List<StructuredContent> findAllContentItems();

    /**
     * 可以是对已经持久化了的StructuredContent地更新(update), 也可以是进行第一次持久化(create)
     *
     * @param content
     * @return 更新或持久化后的StructuredContent
     */
    public StructuredContent addOrUpdateContentItem(StructuredContent content);

    /**
     * 从持久化存储中删去StructuredContent
     *
     * @param content
     */
    public void delete(StructuredContent content);
    
    /**
     * @param contentTypeId StructuredContentType的Id值
     * @return id值等于contentTypeId的StructuredContentType或null, 如果不存在的话
     */
    public StructuredContentType findStructuredContentTypeById(Long contentTypeId);
    
    /**
     * @return 系统中所有的StructuredContentType; 特定于具体的持久化组件, 
     * 如果没有数据的话, 返回的可能是null, 也可能是空列表. 
     */
    public List<StructuredContentType> retrieveAllStructuredContentTypes();
    
    /**
     * 对已经持久化了的StructuredContentType更新(update)
     * 
     * @param type
     * @return 更新后的StructuredContent
     */
    public StructuredContentType saveStructuredContentType(StructuredContentType type);

    /**
     * Pass through function for backwards compatibility to get a list of structured content.
     *
     * @param type of content to search for
     * @param locale to restrict the search to
     * @return a list of all matching content
     * @see com.wakacommerce.cms.web.structure.DisplayContentTag
     */
    public List<StructuredContent> findActiveStructuredContentByType(StructuredContentType type, Locale locale);

    /**
     * Called by the <code>DisplayContentTag</code> to locate content based
     * on the current SandBox, StructuredContentType, fullLocale and/or languageOnlyLocale.
     *
     * @param type of content to search for
     * @param fullLocale to restrict the search to
     * @param languageOnlyLocale locale based only on a language specified
     * @return a list of all matching content
     * @see com.wakacommerce.cms.web.structure.DisplayContentTag
     */
    public List<StructuredContent> findActiveStructuredContentByType(StructuredContentType type, Locale fullLocale, Locale languageOnlyLocale);

    /**
     * Pass through function for backwards compatibility to get a list of structured content.
     *
     * @param sandBox
     * @param type
     * @param name
     * @param locale
     * @return
     */
    public List<StructuredContent> findActiveStructuredContentByNameAndType(StructuredContentType type, String name, Locale locale);

    /**
     * Called by the <code>DisplayContentTag</code> to locate content based
     * on the current SandBox, StructuredContentType, Name, fullLocale and/or languageOnlyLocale.
     *
     * @param sandBox
     * @param type
     * @param name
     * @param fullLocale
     * @param languageOnlyLocale
     * @return
     */
    public List<StructuredContent> findActiveStructuredContentByNameAndType(StructuredContentType type, String name, Locale fullLocale, Locale languageOnlyLocale);

    /**
     * Pass through function for backwards compatibility to get a list of structured content.
     *
     * @param sandBox
     * @param name
     * @param locale
     * @return
     */
    public List<StructuredContent> findActiveStructuredContentByName(String name, Locale locale);

    /**
     * Called by the <code>DisplayContentTag</code> to locate content based
     * on the current SandBox, StructuredContentType, Name, fullLocale and/or languageOnlyLocale.
     *
     * @param sandBox
     * @param name
     * @param fullLocale
     * @param languageOnlyLocale
     * @return
     */
    public List<StructuredContent> findActiveStructuredContentByName(String name, Locale fullLocale, Locale languageOnlyLocale);


    /**
     * Used to lookup the StructuredContentType by name.
     *
     * @param name
     * @return
     */
    public StructuredContentType findStructuredContentTypeByName(String name);

    /**
     * Detaches the item from the JPA session.   This is intended for internal
     * use by the CMS system.   It supports the need to clone an item as part
     * of the editing process.
     *
     * @param sc - the item to detach
     */
    public void detach(StructuredContent sc);
}
