
package com.wakacommerce.openadmin.server.service;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 *
 * @ hui
 */
public class ValidationException extends ServiceException {

    private static final long serialVersionUID = 1L;
    
    protected Entity entity;

    public ValidationException(Entity entity) {
        super();
        setEntity(entity);
    }
    
    public ValidationException(Entity entity, String message) {
        super(message);
        setEntity(entity);
    }
    
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return entity;
    }
    
}
