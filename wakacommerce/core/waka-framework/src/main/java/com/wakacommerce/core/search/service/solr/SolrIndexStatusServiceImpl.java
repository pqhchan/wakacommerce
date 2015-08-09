
package com.wakacommerce.core.search.service.solr;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blSolrIndexStatusService")
public class SolrIndexStatusServiceImpl implements SolrIndexStatusService {

    @Resource(name="blSolrIndexStatusProviders")
    List<SolrIndexStatusProvider> providers;

    @Override
    public synchronized void setIndexStatus(IndexStatusInfo status) {
        for (SolrIndexStatusProvider provider : providers) {
            provider.handleUpdateIndexStatus(status);
        }
    }

    @Override
    public synchronized IndexStatusInfo getIndexStatus() {
        IndexStatusInfo status = getSeedStatusInstance();
        for (SolrIndexStatusProvider provider : providers) {
            provider.readIndexStatus(status);
        }
        return status;
    }

    @Override
    public IndexStatusInfo getSeedStatusInstance() {
        return new IndexStatusInfoImpl();
    }
}
