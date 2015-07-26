
package com.wakacommerce.core.catalog.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.file.service.BroadleafFileUtils;
import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.service.SiteMapBuilder;
import com.wakacommerce.common.sitemap.service.SiteMapGenerator;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import com.wakacommerce.core.catalog.dao.SkuDao;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.Sku;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Responsible for generating site map entries for Sku.
 * 
 *Joshua Skorton (jskorton)
 */
@Component("blSkuSiteMapGenerator")
public class SkuSiteMapGenerator implements SiteMapGenerator {

    @Resource(name = "blSkuDao")
    protected SkuDao skuDao;

    @Value("${sku.site.map.generator.row.limit}")
    protected int pageSize;

    @Override
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.SKU.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {

        int pageNum = 0;
        List<Sku> skus;

        do {
            skus = skuDao.readAllActiveSkus(pageNum++, pageSize);
            for (Sku sku : skus) {
                Product defaultProduct = sku.getDefaultProduct();
                if (defaultProduct != null && CollectionUtils.isNotEmpty(defaultProduct.getAdditionalSkus())) {
                    continue;
                }
                if (defaultProduct instanceof ProductBundle) {
                    continue;
                }
                if (StringUtils.isEmpty(sku.getProduct().getUrl() + sku.getUrlKey())) {
                    continue;
                }
                
                SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

                // location
                siteMapUrl.setLoc(generateUri(siteMapBuilder, sku));

                // change frequency
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());

                // priority
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());

                // lastModDate
                siteMapUrl.setLastModDate(generateDate(sku));

                siteMapBuilder.addUrl(siteMapUrl);
            }
        } while (skus.size() == pageSize);

    }

    protected String generateUri(SiteMapBuilder smb, Sku sku) {
        String uri = null;
        if (sku.getUrlKey() != null) {
            uri = sku.getProduct().getUrl() + sku.getUrlKey();
        } else {
            uri = sku.getProduct().getUrl();
        }
        return BroadleafFileUtils.appendUnixPaths(smb.getBaseUrl(), uri);
    }

    protected Date generateDate(Sku sku) {
        return new Date();
    }
    
    public SkuDao getSkuDao() {
        return skuDao;
    }

    public void setSkuDao(SkuDao skuDao) {
        this.skuDao = skuDao;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
