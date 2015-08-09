
package com.wakacommerce.common.site.dao;

import java.util.List;

import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;

public interface SiteDao {

    public Site create();

    public Site retrieve(Long id);

    public Site retrieveSiteByDomainOrDomainPrefix(String domain, String prefix);

    public Site save(Site site);

    public Site retrieveDefaultSite();

    public List<Site> readAllActiveSites();

    public Catalog retrieveCatalog(Long id);
    
    public Catalog save(Catalog catalog);

    public List<Catalog> retrieveAllCatalogs();
}
