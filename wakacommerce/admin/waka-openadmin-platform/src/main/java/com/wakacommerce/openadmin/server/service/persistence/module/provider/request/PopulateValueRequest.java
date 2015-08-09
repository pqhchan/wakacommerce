
package com.wakacommerce.openadmin.server.service.persistence.module.provider.request;

import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceManager;
import com.wakacommerce.openadmin.server.service.persistence.module.DataFormatProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;

/**
 *
 * @ hui
 */
public class PopulateValueRequest {

    private final Boolean setId;
    private final FieldManager fieldManager;
    private final Property property;
    private final BasicFieldMetadata metadata;
    private final Class<?> returnType;
    private final String requestedValue;
    private final PersistenceManager persistenceManager;
    private final DataFormatProvider dataFormatProvider;

    public PopulateValueRequest(Boolean setId, FieldManager fieldManager, Property property, BasicFieldMetadata metadata, Class<?> returnType, String requestedValue, PersistenceManager persistenceManager, DataFormatProvider dataFormatProvider) {
        this.setId = setId;
        this.fieldManager = fieldManager;
        this.property = property;
        this.metadata = metadata;
        this.returnType = returnType;
        this.requestedValue = requestedValue;
        this.persistenceManager = persistenceManager;
        this.dataFormatProvider = dataFormatProvider;
    }

    public Boolean getSetId() {
        return setId;
    }

    public FieldManager getFieldManager() {
        return fieldManager;
    }

    public Property getProperty() {
        return property;
    }

    public BasicFieldMetadata getMetadata() {
        return metadata;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public String getRequestedValue() {
        return requestedValue;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public DataFormatProvider getDataFormatProvider() {
        return dataFormatProvider;
    }
}
