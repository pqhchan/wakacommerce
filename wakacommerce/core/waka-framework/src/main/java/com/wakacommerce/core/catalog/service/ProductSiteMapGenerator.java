
package com.wakacommerce.core.catalog.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import com.wakacommerce.common.sitemap.service.SiteMapBuilder;
import com.wakacommerce.common.sitemap.service.SiteMapGenerator;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import com.wakacommerce.core.catalog.dao.ProductDao;
import com.wakacommerce.core.catalog.domain.Product;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Responsible for generating site map entries for Product.
 * 
 *  
 */
@Component("blProductSiteMapGenerator")
public class ProductSiteMapGenerator implements SiteMapGenerator {

    @Resource(name = "blProductDao")
    protected ProductDao productDao;

    @Value("${product.site.map.generator.row.limit}")
    protected int pageSize;

    @Override
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.PRODUCT.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {

        int pageNum = 0;
        List<Product> products;

        do {
            products = productDao.readAllActiveProducts(pageNum++, pageSize);
            for (Product product : products) {
                if (StringUtils.isEmpty(product.getUrl())) {
                    continue;
                }

                SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

                // location
                siteMapUrl.setLoc(generateUri(siteMapBuilder, product));

                // change frequency
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());

                // priority
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());

                // lastModDate
                siteMapUrl.setLastModDate(generateDate(product));

                siteMapBuilder.addUrl(siteMapUrl);
            }
        } while (products.size() == pageSize);

    }

    protected String generateUri(SiteMapBuilder smb, Product product) {
        return WakaFileUtils.appendUnixPaths(smb.getBaseUrl(), product.getUrl());
    }

    protected Date generateDate(Product product) {
        return new Date();
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
