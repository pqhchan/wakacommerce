
package com.wakacommerce.openadmin.server.service.persistence.module.provider.request;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceManager;
import com.wakacommerce.openadmin.server.service.persistence.module.DataFormatProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

/**
 *
 * @ hui
 */
public class ExtractValueRequest {

    protected final List<Property> props;
    protected final FieldManager fieldManager;
    protected final BasicFieldMetadata metadata;
    protected final Object requestedValue;
    protected String displayVal;
    protected final PersistenceManager persistenceManager;
    protected final RecordHelper recordHelper;
    protected final Serializable entity;

    public ExtractValueRequest(List<Property> props, FieldManager fieldManager, BasicFieldMetadata metadata, 
            Object requestedValue, String displayVal, PersistenceManager persistenceManager, 
            RecordHelper recordHelper, Serializable entity) {
        this.props = props;
        this.fieldManager = fieldManager;
        this.metadata = metadata;
        this.requestedValue = requestedValue;
        this.displayVal = displayVal;
        this.persistenceManager = persistenceManager;
        this.recordHelper = recordHelper;
        this.entity = entity;
    }

    public List<Property> getProps() {
        return props;
    }

    public FieldManager getFieldManager() {
        return fieldManager;
    }

    public BasicFieldMetadata getMetadata() {
        return metadata;
    }

    public Object getRequestedValue() {
        return requestedValue;
    }

    public String getDisplayVal() {
        return displayVal;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public DataFormatProvider getDataFormatProvider() {
        return recordHelper;
    }
    
    public RecordHelper getRecordHelper() {
        return recordHelper;
    }

    public void setDisplayVal(String displayVal) {
        this.displayVal = displayVal;
    }
    
    public Serializable getEntity() {
        return entity;
    }
    
}
