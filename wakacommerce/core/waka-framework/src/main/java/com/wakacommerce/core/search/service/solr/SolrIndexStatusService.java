
package com.wakacommerce.core.search.service.solr;

/**
 *
 * @ hui
 */
public interface SolrIndexStatusService {

    void setIndexStatus(IndexStatusInfo status);

    IndexStatusInfo getIndexStatus();

    IndexStatusInfo getSeedStatusInstance();

}
