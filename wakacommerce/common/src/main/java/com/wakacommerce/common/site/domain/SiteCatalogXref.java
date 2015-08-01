
package com.wakacommerce.common.site.domain;

import java.io.Serializable;

/**
 * Defines the explicit join between a {@link Site} and {@link Catalog}
 * 
 *     
 * @see {@link Site#getCatalogXrefs()}
 * @see {@link Catalog#getSiteXrefs()}
 */
public interface SiteCatalogXref extends Serializable {
    
    public Site getSite();
    
    public void setSite(Site site);
    
    public Catalog getCatalog();
    
    public void setCatalog(Catalog catalog);

    Long getId();

    void setId(Long id);

}
