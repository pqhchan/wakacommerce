
package com.wakacommerce.core.search.service.solr;

/**
 *
 * @ hui
 */
public interface SolrIndexStatusProvider {

    void handleUpdateIndexStatus(IndexStatusInfo status);

    IndexStatusInfo readIndexStatus(IndexStatusInfo status);

}
