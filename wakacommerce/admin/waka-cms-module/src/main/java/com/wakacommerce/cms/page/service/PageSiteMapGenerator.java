 
package com.wakacommerce.cms.page.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wakacommerce.cms.page.dao.PageDao;
import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.service.SiteMapBuilder;
import com.wakacommerce.common.sitemap.service.SiteMapGenerator;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Responsible for generating site map entries for Page.
 * 
 *  
 */
@Component("blPageSiteMapGenerator")
public class PageSiteMapGenerator implements SiteMapGenerator {

    @Resource(name = "blPageDao")
    protected PageDao pageDao;

    @Value("${page.site.map.generator.row.limit}")
    protected int rowLimit;

    @Override
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.PAGE.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {

        int rowOffset = 0;
        List<Page> pages;
        String previousUrl = "";

        do {
            pages = pageDao.readOnlineAndIncludedPages(rowLimit, rowOffset, "fullUrl");
            rowOffset += pages.size();
            for (Page page : pages) {

                if (page.getExcludeFromSiteMap()) {
                    continue;
                }

                String currentURL = page.getFullUrl();

                if (previousUrl.equals(currentURL)) {
                    continue;
                } else {
                    previousUrl = currentURL;
                }

                SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

                // location
                siteMapUrl.setLoc(generateUri(siteMapBuilder, page));

                // change frequency
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());

                // priority
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());

                // lastModDate
                siteMapUrl.setLastModDate(generateDate(page));

                siteMapBuilder.addUrl(siteMapUrl);
            }
        } while (pages.size() == rowLimit);
    }

    protected String generateUri(SiteMapBuilder smb, Page page) {
        return WakaFileUtils.appendUnixPaths(smb.getBaseUrl(), page.getFullUrl());
    }

    protected Date generateDate(Page page) {
        if ((page.getAuditable() != null) && (page.getAuditable().getDateUpdated() != null)) {
            return page.getAuditable().getDateUpdated();
        } else {
            return new Date();
        }
    }

    public PageDao getPageDao() {
        return pageDao;
    }

    public void setPageDao(PageDao pageDao) {
        this.pageDao = pageDao;
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(int rowLimit) {
        this.rowLimit = rowLimit;
    }

}
