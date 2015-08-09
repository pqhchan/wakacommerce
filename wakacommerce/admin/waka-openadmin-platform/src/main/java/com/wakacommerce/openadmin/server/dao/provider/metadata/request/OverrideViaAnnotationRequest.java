
package com.wakacommerce.openadmin.server.dao.provider.metadata.request;

import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;

/**
 *
 * @ hui
 */
public class OverrideViaAnnotationRequest {

    private final Class<?> requestedEntity;
    private final Boolean parentExcluded;
    private final DynamicEntityDao dynamicEntityDao;
    private final String prefix;

    public OverrideViaAnnotationRequest(Class<?> requestedEntity, Boolean parentExcluded, DynamicEntityDao dynamicEntityDao, String prefix) {
        this.requestedEntity = requestedEntity;
        this.parentExcluded = parentExcluded;
        this.dynamicEntityDao = dynamicEntityDao;
        this.prefix = prefix;
    }

    public Class<?> getRequestedEntity() {
        return requestedEntity;
    }

    public Boolean getParentExcluded() {
        return parentExcluded;
    }

    public DynamicEntityDao getDynamicEntityDao() {
        return dynamicEntityDao;
    }

    public String getPrefix() {
        return prefix;
    }
}
