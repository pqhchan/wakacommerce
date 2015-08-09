 
package com.wakacommerce.cms.page.service;

import net.sf.ehcache.Cache;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.cms.page.domain.PageField;
import com.wakacommerce.cms.page.domain.PageTemplate;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.page.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface PageService {

    public Page findPageById(Long pageId);

    public Map<String, PageField> findPageFieldMapByPageId(Long pageId);

    public PageTemplate findPageTemplateById(Long id);

    public PageTemplate savePageTemplate(PageTemplate template);

    public PageDTO findPageByURI(Locale locale, String uri, Map<String,Object> ruleDTOs, boolean secure);

    public List<Page> readAllPages();

    public List<PageTemplate> readAllPageTemplates();

    public void removePageFromCache(String key);

    Cache getPageCache();

    Cache getPageMapCache();

    public List<PageDTO> buildPageDTOList(List<Page> pageList, boolean secure);

    String getPageMapCacheKey(String uri, Long sandBox, Long site);


}
