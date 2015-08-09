

package com.wakacommerce.common.sitemap.domain;

import java.io.Serializable;

import com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType;

/**
 *
 * @ hui
 */
public interface SiteMapGeneratorConfiguration extends Serializable {

    public Long getId();

    public void setId(Long id);

    public Boolean isDisabled();

    public void setDisabled(Boolean disabled);

    public SiteMapChangeFreqType getSiteMapChangeFreq();

    public void setSiteMapChangeFreq(SiteMapChangeFreqType siteMapChangeFreq);

    public SiteMapPriorityType getSiteMapPriority();

    public void setSiteMapPriority(SiteMapPriorityType siteMapPriority);

    public SiteMapGeneratorType getSiteMapGeneratorType();

    public void setSiteMapGeneratorType(SiteMapGeneratorType siteMapGeneratorType);

    public SiteMapConfiguration getSiteMapConfiguration();

    public void setSiteMapConfiguration(SiteMapConfiguration siteMapConfiguration);

}
