package com.wakacommerce.common.sitemap.domain;

import java.io.Serializable;

import com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType;

/**
 * Sample URL tag generated and controlled by this configuration.
 * 
 * <url>
 *   <loc>http://www.heatclinic.com/hot-sauces</loc>
 *   <lastmod>2009-11-07</lastmod>
 *   <changefreq>weekly</changefreq>
 *   <priority>0.5</priority>
 * </url>
 */
public interface SiteMapGeneratorConfiguration extends Serializable {
    
    public Long getId();

    public void setId(Long id);

    public Boolean isDisabled();

    public void setDisabled(Boolean disabled);

    public SiteMapChangeFreqType getSiteMapChangeFreq();

    public void setSiteMapChangeFreq(SiteMapChangeFreqType siteMapChangeFreq);

    public SiteMapPriorityType getSiteMapPriority();

    /**
     * 0.0 到 1.0.
     * 
     * @param siteMapPriority
     */
    public void setSiteMapPriority(SiteMapPriorityType siteMapPriority);

    public SiteMapGeneratorType getSiteMapGeneratorType();
    
    public void setSiteMapGeneratorType(SiteMapGeneratorType siteMapGeneratorType);

    public SiteMapConfiguration getSiteMapConfiguration();

    public void setSiteMapConfiguration(SiteMapConfiguration siteMapConfiguration);

}
