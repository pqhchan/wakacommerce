package com.wakacommerce.common.sitemap.domain;

import java.util.List;

/**
 *
 * @ hui
 */
public interface CustomUrlSiteMapGeneratorConfiguration extends SiteMapGeneratorConfiguration {

    public List<SiteMapUrlEntry> getCustomURLEntries();

    public void setCustomURLEntries(List<SiteMapUrlEntry> customURLEntries);

}
