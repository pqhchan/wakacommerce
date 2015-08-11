package com.wakacommerce.common.sitemap.service;

import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;


/**
 *
 * @ hui
 */
public interface SiteMapGenerator {

    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration);

    public void addSiteMapEntries(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration, SiteMapBuilder siteMapBuilder);

}
