  
package com.wakacommerce.core.search.service.solr;

import org.apache.commons.collections4.MapUtils;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.search.dao.CatalogStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a single cache while exposing a block of code for execution to
 * {@link com.wakacommerce.core.search.service.solr.SolrIndexService#performCachedOperation(com.wakacommerce.core.search.service.solr.SolrIndexCachedOperation.CacheOperation)}.
 * This serves to boost performance while executing multiple calls to {@link com.wakacommerce.core.search.service.solr.SolrIndexService#buildIncrementalIndex(int, int, boolean)}.
 *
 * @see com.wakacommerce.core.search.service.solr.SolrIndexService
 * 
 */
public class SolrIndexCachedOperation {

    public static final Long DEFAULT_CATALOG_CACHE_KEY = 0l;
    
    private static final ThreadLocal<Map<Long, CatalogStructure>> CACHE = new ThreadLocal<Map<Long, CatalogStructure>>();

    /**
     * Retrieve the cache bound to the current thread.
     *
     * @return The cache for the current thread, or null if not set
     */
    public static CatalogStructure getCache() {
        WakaRequestContext ctx = WakaRequestContext.getWakaRequestContext();
        Catalog currentCatalog = ctx == null ? null : ctx.getCurrentCatalog();
        if (currentCatalog != null) {
            return MapUtils.getObject(CACHE.get(), currentCatalog.getId());
        } else {
            return MapUtils.getObject(CACHE.get(), DEFAULT_CATALOG_CACHE_KEY);
        }
    }

    /**
     * Set the cache on the current thread
     *
     * @param cache the cache object (usually an empty map)
     */
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

    /**
     * Clear the thread local cache from the thread
     */
    public static void clearCache() {
        CACHE.remove();
    }

    /**
     * Basic interface representing a block of work to perform with a single cache instance
     */
    public interface CacheOperation {

        /**
         * Execute the block of work
         *
         * @throws ServiceException
         */
        void execute() throws ServiceException;

    }
}
