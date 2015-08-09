
package com.wakacommerce.common.site.domain;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.common.persistence.ArchiveStatus;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.common.site.service.type.SiteResolutionType;

/**
 *
 * @ hui
 */
public interface Site extends Serializable, Status {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    @Deprecated
    public String getSiteIdentifierType();

    @Deprecated
    public void setSiteIdentifierType(String siteIdentifierType);

    public String getSiteIdentifierValue();

    public void setSiteIdentifierValue(String siteIdentifierValue);

    public SiteResolutionType getSiteResolutionType();

    public void setSiteResolutionType(SiteResolutionType siteResolutionType);

    @Deprecated
    public List<Catalog> getCatalogs();

    @Deprecated
    public void setCatalogs(List<Catalog> catalogs);

    public Site clone();
    
    public ArchiveStatus getArchiveStatus();

    public boolean isDeactivated();

    public void setDeactivated(boolean deactivated);

    @Deprecated
    public boolean isTemplateSite();
}
