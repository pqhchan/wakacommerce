package com.wakacommerce.cms.page.service;

import java.util.List;
import java.util.Map;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.cms.page.domain.PageField;
import com.wakacommerce.cms.page.domain.PageTemplate;
import com.wakacommerce.common.page.dto.PageDTO;

import net.sf.ehcache.Cache;

public interface PageService {

    /**
     * Returns the page with the passed in id.
     *
     * @param pageId - The id of the page.
     * @return The associated page.
     */
    public Page findPageById(Long pageId);

    /**
     * Returns the page-fields associated with a page.
     * @param pageId
     * @return
     */
    public Map<String, PageField> findPageFieldMapByPageId(Long pageId);

    /**
     * Returns the page template with the passed in id.
     *
     * @param id - the id of the page template
     * @return The associated page template.
     */
    public PageTemplate findPageTemplateById(Long id);
    
    /**
     * Saves the given {@link PageTemplate}
     * 
     * @param template the {@link PageTemplate} to save
     * @return the database-saved {@link PageTemplate}
     */
    public PageTemplate savePageTemplate(PageTemplate template);

    /**
     * Looks up the page from the backend datastore.   Processes the page's fields to
     * fix the URL if the site has overridden the URL for images.   If secure is true
     * and images are being overridden, the system will use https.
     *
     * @param uri - the URI to return a page for
     * @param ruleDTOs - ruleDTOs that are used as the data to process page rules
     * @param secure - set to true if current request is over HTTPS
     * @return
     */
    public PageDTO findPageByURI(String uri, Map<String,Object> ruleDTOs, boolean secure);
    
    /**
     * Returns all pages, regardless of any sandbox they are apart of
     * @return all {@link Page}s configured in the system
     */
    public List<Page> readAllPages();
    
    /**
     * Returns all page templates, regardless of any sandbox they are apart of
     * @return all {@link PageTemplate}s configured in the system
     */
    public List<PageTemplate> readAllPageTemplates();

    /**
     * Call to evict all known PageDTOs that are associated with the given page from cache
     * 
     * @param key
     */
    public void removePageFromCache(String key);

    Cache getPageCache();

    Cache getPageMapCache();

    /**
     * Builds a list of {@link PageDTO} objects from the given list of {@link Page} objects.
     * 
     * @param pageList
     * @param secure
     * @return
     */
    public List<PageDTO> buildPageDTOList(List<Page> pageList, boolean secure);

    String getPageMapCacheKey(String uri, Long sandBox, Long site);


}
