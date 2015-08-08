package com.wakacommerce.cms.page.dao;

import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.cms.page.domain.PageField;
import com.wakacommerce.cms.page.domain.PageTemplate;

public interface PageDao {

    public Page readPageById(Long id);
    
    public List<PageField> readPageFieldsByPageId(Long pageId);

    public PageTemplate readPageTemplateById(Long id);
    
    public PageTemplate savePageTemplate(PageTemplate template);

    public Page updatePage(Page page);

    public void delete(Page page);

    public Page addPage(Page clonedPage);

    public List<Page> readAllPages();
    
    @Nonnull
    public List<Page> readOnlineAndIncludedPages(@Nonnull int limit, @Nonnull int offset, @Nonnull String sortBy);

    public List<PageTemplate> readAllPageTemplates();

    public List<Page> findPageByURI(String uri);
    
    public void detachPage(Page page);

}
