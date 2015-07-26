
package com.wakacommerce.common.site.domain;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.common.persistence.Status;

/**
 *Jeff Fischer
 */
public interface Catalog extends Status, Serializable {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    @Deprecated
    List<Site> getSites();

    @Deprecated
    void setSites(List<Site> sites);
    
    public List<SiteCatalogXref> getSiteXrefs();

    public void setSiteXrefs(List<SiteCatalogXref> siteXrefs);

}
