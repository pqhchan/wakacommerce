
package com.wakacommerce.core.search.service.solr;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Manages extension points for SolrSearchService
 *bpolster
 */
@Service("blSolrSearchServiceExtensionManager")
public class SolrSearchServiceExtensionManager extends ExtensionManager<SolrSearchServiceExtensionHandler> {

    public SolrSearchServiceExtensionManager() {
        super(SolrSearchServiceExtensionHandler.class);
    }

}
