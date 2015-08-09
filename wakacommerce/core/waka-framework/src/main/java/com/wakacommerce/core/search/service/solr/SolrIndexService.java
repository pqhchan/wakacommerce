
package com.wakacommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.Field;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @ hui
 */
public interface SolrIndexService {

    public void rebuildIndex() throws ServiceException, IOException;

    public boolean isReindexInProcess();

    public void buildIncrementalIndex(int page, int pageSize, boolean useReindexServer) throws ServiceException;

    public void buildIncrementalSkuIndex(List<Sku> skus, boolean useReindexServer) throws ServiceException;

    public void buildIncrementalProductIndex(List<Product> products, boolean useReindexServer) throws ServiceException;

    public Object[] saveState();

    public void restoreState(Object[] pack);

    public void optimizeIndex(SolrServer server) throws ServiceException, IOException;


    public void commit(SolrServer server) throws ServiceException, IOException;

    public void commit(SolrServer server, boolean softCommit, boolean waitSearcher, boolean waitFlush) throws ServiceException, IOException;

    public void logDocuments(Collection<SolrInputDocument> documents);

    public List<Locale> getAllLocales();

    public SolrInputDocument buildDocument(Product product, List<Field> fields, List<Locale> locales);

    public SolrInputDocument buildDocument(Sku sku, List<Field> fields, List<Locale> locales);

    public void performCachedOperation(SolrIndexCachedOperation.CacheOperation cacheOperation) throws ServiceException;
}