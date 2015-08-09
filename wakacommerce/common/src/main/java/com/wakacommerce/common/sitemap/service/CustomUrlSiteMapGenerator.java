

package com.wakacommerce.common.sitemap.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.sitemap.domain.CustomUrlSiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.domain.SiteMapUrlEntry;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;

import java.util.Date;

/**
 *
 * @ hui
 */
@Component("blCustomSiteMapGenerator")
public class CustomUrlSiteMapGenerator implements SiteMapGenerator {

    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.CUSTOM.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {
        for (SiteMapUrlEntry urlEntry : ((CustomUrlSiteMapGeneratorConfiguration) smgc).getCustomURLEntries()) {
            if (StringUtils.isEmpty(urlEntry.getLocation())) {
                continue;
            }
            SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

            // location
            siteMapUrl.setLoc(generateUri(siteMapBuilder, urlEntry));

            // change frequency
            if (urlEntry.getSiteMapChangeFreq() != null) {
                siteMapUrl.setChangeFreqType(urlEntry.getSiteMapChangeFreq());
            } else {
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());
            }

            // priority
            if (urlEntry.getSiteMapPriority() != null) {
                siteMapUrl.setPriorityType(urlEntry.getSiteMapPriority());
            } else {
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());
            }

            // lastModDate
            siteMapUrl.setLastModDate(generateDate(urlEntry));
            
            siteMapBuilder.addUrl(siteMapUrl);
        }
    }
    
    protected String generateUri(SiteMapBuilder smb, SiteMapUrlEntry urlEntry) {
        String url = urlEntry.getLocation();
        if (url.contains("://")) {
            return url;
        } else {
            return WakaFileUtils.appendUnixPaths(smb.getBaseUrl(), url);
        }
    }

    protected Date generateDate(SiteMapUrlEntry urlEntry) {
        if(urlEntry.getLastMod() != null) {
            return urlEntry.getLastMod();
        } else {
            return new Date();
        }
    }

}
