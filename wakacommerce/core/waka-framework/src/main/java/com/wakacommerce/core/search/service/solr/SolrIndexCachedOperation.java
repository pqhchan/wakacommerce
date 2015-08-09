
package com.wakacommerce.core.search.service.solr;

import org.apache.commons.collections4.MapUtils;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.search.dao.CatalogStructure;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class SolrIndexCachedOperation {

    public static final Long DEFAULT_CATALOG_CACHE_KEY = 0l;
    
    private static final ThreadLocal<Map<Long, CatalogStructure>> CACHE = new ThreadLocal<Map<Long, CatalogStructure>>();

    public static CatalogStructure getCache() {
        WakaRequestContext ctx = WakaRequestContext.getWakaRequestContext();
        Catalog currentCatalog = ctx == null ? null : ctx.getCurrentCatalog();
        if (currentCatalog != null) {
            return MapUtils.getObject(CACHE.get(), currentCatalog.getId());
        } else {
            return MapUtils.getObject(CACHE.get(), DEFAULT_CATALOG_CACHE_KEY);
        }
    }

    public static void setCache(CatalogStructure cache) {
        WakaRequestContext ctx = WakaRequestContext.getWakaRequestContext();
        Catalog currentCatalog = ctx == null ? null : ctx.getCurrentCatalog();
        Map<Long, CatalogStructure> catalogCaches = CACHE.get();
        if (catalogCaches == null) {
            catalogCaches = new HashMap<Long, CatalogStructure>();
            CACHE.set(catalogCaches);
        }
        if (currentCatalog != null) {
            catalogCaches.put(currentCatalog.getId(), cache);
        } else {
            catalogCaches.put(DEFAULT_CATALOG_CACHE_KEY, cache);
        }
    }

    public static void clearCache() {
        CACHE.remove();
    }

    public interface CacheOperation {

        void execute() throws ServiceException;

    }
}
