
package com.wakacommerce.openadmin.dto;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public class EntityResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Entity entity;
    private Object entityBackingObject;
    
    public Entity getEntity() {
        return entity;
    }
    
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public Object getEntityBackingObject() {
        return entityBackingObject;
    }
    
    public void setEntityBackingObject(Object entityBackingObject) {
        this.entityBackingObject = entityBackingObject;
    }
}
