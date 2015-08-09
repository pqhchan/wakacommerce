

package com.wakacommerce.common.sitemap.domain;

import java.io.Serializable;
import java.util.Date;

import com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType;
import com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType;

/**
 *
 * @ hui
 */
public interface SiteMapUrlEntry extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getLocation();

    public void setLocation(String location);

    public Date getLastMod();

    public void setLastMod(Date date);

    public SiteMapChangeFreqType getSiteMapChangeFreq();

    public void setSiteMapChangeFreq(SiteMapChangeFreqType siteMapChangeFreq);

    public SiteMapPriorityType getSiteMapPriority();

    public void setSiteMapPriority(SiteMapPriorityType siteMapPriority);

    public CustomUrlSiteMapGeneratorConfiguration getCustomUrlSiteMapGeneratorConfiguration();

    public void setCustomUrlSiteMapGeneratorConfiguration(CustomUrlSiteMapGeneratorConfiguration customUrlSiteMapGeneratorConfiguration);

}
