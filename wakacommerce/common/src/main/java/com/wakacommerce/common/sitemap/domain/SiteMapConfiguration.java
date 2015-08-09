

package com.wakacommerce.common.sitemap.domain;

import java.util.List;

import com.wakacommerce.common.config.domain.ModuleConfiguration;


/**
 *
 * @ hui
 */
public interface SiteMapConfiguration extends ModuleConfiguration {

    List<SiteMapGeneratorConfiguration> getSiteMapGeneratorConfigurations();

    void setSiteMapGeneratorConfigurations(List<SiteMapGeneratorConfiguration> siteMapGeneratorConfigurations);

    Integer getMaximumUrlEntriesPerFile();

    void setMaximumUrlEntriesPerFile(Integer maximumUrlEntriesPerFile);

    String fixSiteUrlPath(String siteUrlPath);

    String getSiteMapFileName();

    void setSiteMapFileName(String siteMapFileName);

    String getIndexedSiteMapFileName();

    void setIndexedSiteMapFileName(String fileName);

    String getSiteMapIndexFilePattern();

    void setIndexedSiteMapFilePattern(String filePattern);

}
