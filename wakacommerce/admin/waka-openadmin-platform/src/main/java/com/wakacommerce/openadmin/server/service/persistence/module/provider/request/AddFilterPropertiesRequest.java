
package com.wakacommerce.openadmin.server.service.persistence.module.provider.request;

import com.wakacommerce.openadmin.dto.Entity;

/**
 *
 * @ hui
 */
public class AddFilterPropertiesRequest {

    private final Entity entity;

    public AddFilterPropertiesRequest(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
