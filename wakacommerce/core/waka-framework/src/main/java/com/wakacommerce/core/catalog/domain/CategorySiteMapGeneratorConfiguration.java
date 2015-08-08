package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;

public interface CategorySiteMapGeneratorConfiguration extends SiteMapGeneratorConfiguration {

    public Category getRootCategory();

    public void setRootCategory(Category rootCategory);

    public int getStartingDepth();

    public void setStartingDepth(int startingDepth);

    public int getEndingDepth();

    public void setEndingDepth(int endingDepth);

}
