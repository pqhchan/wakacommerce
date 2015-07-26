
package com.wakacommerce.openadmin.server.service.handler;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.openadmin.dto.Entity;

/**
 *Jeff Fischer
 */
public interface DynamicEntityRetriever {

    Entity fetchDynamicEntity(Serializable root, List<String> dirtyFields, boolean includeId) throws Exception;

    Entity fetchEntityBasedOnId(String themeConfigurationId, List<String> dirtyFields) throws Exception;

    String getFieldContainerClassName();

}
