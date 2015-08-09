
package com.wakacommerce.core.search.dao;

import java.util.List;

/**
 *
 * @ hui
 */
public interface SolrIndexDao {

    void populateProductCatalogStructure(List<Long> productIds, CatalogStructure catalogStructure);

}
