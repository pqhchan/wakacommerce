  
package com.wakacommerce.core.search.dao;

import java.util.List;

/**
 * Provides some specialized catalog retrieval methods for {@link com.wakacommerce.core.search.service.solr.SolrIndexService} for maximum
 * efficiency of solr document creation during indexing.
 *
 * 
 */
public interface SolrIndexDao {

    /**
     * Populate the contents of a lightweight catalog structure for a list of products.
     *
     * @param productIds
     * @param catalogStructure lightweight container defining product and category hierarchies
     * @see com.wakacommerce.core.search.dao.CatalogStructure
     */
    void populateProductCatalogStructure(List<Long> productIds, CatalogStructure catalogStructure);

}
