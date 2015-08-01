/*
 * #%L
 * BroadleafCommerce Framework
 * %%
 * Copyright (C) 2009 - 2014 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
