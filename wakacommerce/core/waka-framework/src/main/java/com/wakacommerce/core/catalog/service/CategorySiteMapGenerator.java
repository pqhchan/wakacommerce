
package com.wakacommerce.core.catalog.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.service.SiteMapBuilder;
import com.wakacommerce.common.sitemap.service.SiteMapGenerator;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import com.wakacommerce.core.catalog.dao.CategoryDao;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategorySiteMapGeneratorConfiguration;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Component("blCategorySiteMapGenerator")
public class CategorySiteMapGenerator implements SiteMapGenerator {

    protected static final Log LOG = LogFactory.getLog(CategorySiteMapGenerator.class);

    @Resource(name = "blCategoryDao")
    protected CategoryDao categoryDao;

    @Value("${category.site.map.generator.row.limit}")
    protected int rowLimit;

    @Override
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.CATEGORY.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {

        CategorySiteMapGeneratorConfiguration categorySMGC = (CategorySiteMapGeneratorConfiguration) smgc;

        // Recursively construct the category SiteMap URLs
        addCategorySiteMapEntries(categorySMGC.getRootCategory(), 0, categorySMGC, siteMapBuilder);
        
    }

    protected void addCategorySiteMapEntries(Category parentCategory, int currentDepth, CategorySiteMapGeneratorConfiguration categorySMGC, SiteMapBuilder siteMapBuilder) {
        // If we've reached beyond the ending depth, don't proceed
        if (currentDepth > categorySMGC.getEndingDepth()) {
            return;
        }

        // If we're at or past the starting depth, add this category to the site map
        if (currentDepth >= categorySMGC.getStartingDepth()) {
            constructSiteMapURL(categorySMGC, siteMapBuilder, parentCategory);
        }

        // Recurse on child categories in batches of size rowLimit
        int rowOffset = 0;
        List<Category> categories;
        do {
            categories = categoryDao.readActiveSubCategoriesByCategory(parentCategory, rowLimit, rowOffset);
            rowOffset += categories.size();
            for (Category category : categories) {
                if (StringUtils.isNotEmpty(category.getUrl())) {
                    addCategorySiteMapEntries(category, currentDepth + 1, categorySMGC, siteMapBuilder);
                } else {
                    LOG.debug("Skipping empty category URL: " + category.getId());
                }
            }
        } while (categories.size() == rowLimit);
    }

    protected void constructSiteMapURL(CategorySiteMapGeneratorConfiguration categorySMGC, SiteMapBuilder siteMapBuilder, Category category) {
        SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

        // location
        siteMapUrl.setLoc(generateUri(siteMapBuilder, category));

        // change frequency
        siteMapUrl.setChangeFreqType(categorySMGC.getSiteMapChangeFreq());

        // priority
        siteMapUrl.setPriorityType(categorySMGC.getSiteMapPriority());

        // lastModDate
        siteMapUrl.setLastModDate(generateDate(category));

        siteMapBuilder.addUrl(siteMapUrl);
    }

    protected String generateUri(SiteMapBuilder siteMapBuilder, Category category) {
        return WakaFileUtils.appendUnixPaths(siteMapBuilder.getBaseUrl(), category.getUrl());
    }

    protected Date generateDate(Category category) {
        return new Date();
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(int rowLimit) {
        this.rowLimit = rowLimit;
    }

}
