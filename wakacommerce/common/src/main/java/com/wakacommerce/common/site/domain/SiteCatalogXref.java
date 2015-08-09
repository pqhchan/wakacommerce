
package com.wakacommerce.common.site.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface SiteCatalogXref extends Serializable {
    
    public Site getSite();
    
    public void setSite(Site site);
    
    public Catalog getCatalog();
    
    public void setCatalog(Catalog catalog);

    Long getId();

    void setId(Long id);

}
