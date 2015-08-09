
package com.wakacommerce.common.site.service;

import java.util.List;

import com.wakacommerce.common.site.dao.SiteDao;
import com.wakacommerce.common.site.dao.SiteDaoImpl;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.web.BroadleafSiteResolver;

/**
 *
 * @ hui
 */
public interface SiteService {

    public Site createSite();

    @Deprecated
    public Site retrieveSiteById(Long id);

    public Site retrieveNonPersistentSiteById(Long id);

    public Site retrievePersistentSiteById(Long id);

    @Deprecated
    public Site retrieveSiteByDomainName(String domain);

    public Site retrieveNonPersistentSiteByDomainName(String domain);

    public Site retrievePersistentSiteByDomainName(String domain);

    @Deprecated
    public Site save(Site site);

    public Site saveAndReturnNonPersisted(Site site);

    public Site saveAndReturnPersisted(Site site);

    @Deprecated
    public Site retrieveDefaultSite();

    public Site retrieveNonPersistentDefaultSite();

    public Site retrievePersistentDefaultSite();

    @Deprecated
    public List<Site> findAllActiveSites();

    public List<Site> findAllNonPersistentActiveSites();

    public List<Site> findAllPersistentActiveSites();

    public Catalog findCatalogById(Long id);

    public Catalog save(Catalog catalog);

    public List<Catalog> findAllCatalogs();

}
