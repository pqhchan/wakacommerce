
package com.wakacommerce.openadmin.server.service.persistence;

import java.util.HashMap;
import java.util.Map;

import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;

/**
 *Jeff Fischer
 */
public class PersistenceManagerEventHandlerResponse {

    protected Entity entity;
    protected DynamicResultSet dynamicResultSet;
    protected PersistenceManagerEventHandlerResponseStatus status;
    protected Map<String, Object> additionalData = new HashMap<String, Object>();

    public enum PersistenceManagerEventHandlerResponseStatus {
        HANDLED,NOT_HANDLED,HANDLED_BREAK
    }

    public PersistenceManagerEventHandlerResponse withEntity(Entity entity) {
        setEntity(entity);
        return this;
    }

    public PersistenceManagerEventHandlerResponse withStatus(PersistenceManagerEventHandlerResponseStatus status) {
        setStatus(status);
        return this;
    }

    public PersistenceManagerEventHandlerResponse withDynamicResultSet(DynamicResultSet dynamicResultSet) {
        setDynamicResultSet(dynamicResultSet);
        return this;
    }

    public PersistenceManagerEventHandlerResponse withAdditionalData(Map<String, Object> additionalData) {
        setAdditionalData(additionalData);
        return this;
    }

    public DynamicResultSet getDynamicResultSet() {
        return dynamicResultSet;
    }

    public void setDynamicResultSet(DynamicResultSet dynamicResultSet) {
        this.dynamicResultSet = dynamicResultSet;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public PersistenceManagerEventHandlerResponseStatus getStatus() {
        return status;
    }

    public void setStatus(PersistenceManagerEventHandlerResponseStatus status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }
}
