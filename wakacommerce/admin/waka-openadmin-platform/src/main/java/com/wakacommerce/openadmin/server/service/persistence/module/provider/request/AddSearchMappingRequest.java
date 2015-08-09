
package com.wakacommerce.openadmin.server.service.persistence.module.provider.request;

import java.util.Map;

import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.service.persistence.module.DataFormatProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory;

/**
 *
 * @ hui
 */
public class AddSearchMappingRequest {

    private final PersistencePerspective persistencePerspective;
    private final CriteriaTransferObject requestedCto;
    private final String ceilingEntityFullyQualifiedClassname;
    private final Map<String, FieldMetadata> mergedProperties;
    private final String propertyName;
    private final FieldManager fieldManager;
    private final DataFormatProvider dataFormatProvider;
    private final RecordHelper recordHelper;
    private final RestrictionFactory restrictionFactory;

    public AddSearchMappingRequest(PersistencePerspective persistencePerspective, CriteriaTransferObject
            requestedCto, String ceilingEntityFullyQualifiedClassname, Map<String, FieldMetadata> mergedProperties,
                                   String propertyName, FieldManager fieldManager,
                                   DataFormatProvider dataFormatProvider, RecordHelper recordHelper,
                                   RestrictionFactory restrictionFactory) {
        this.persistencePerspective = persistencePerspective;
        this.requestedCto = requestedCto;
        this.ceilingEntityFullyQualifiedClassname = ceilingEntityFullyQualifiedClassname;
        this.mergedProperties = mergedProperties;
        this.propertyName = propertyName;
        this.fieldManager = fieldManager;
        this.dataFormatProvider = dataFormatProvider;
        this.recordHelper = recordHelper;
        this.restrictionFactory = restrictionFactory;
    }

    public PersistencePerspective getPersistencePerspective() {
        return persistencePerspective;
    }

    public CriteriaTransferObject getRequestedCto() {
        return requestedCto;
    }

    public String getCeilingEntityFullyQualifiedClassname() {
        return ceilingEntityFullyQualifiedClassname;
    }

    public Map<String, FieldMetadata> getMergedProperties() {
        return mergedProperties;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public FieldManager getFieldManager() {
        return fieldManager;
    }
    
    public DataFormatProvider getDataFormatProvider() {
        return dataFormatProvider;
    }
    
    public RecordHelper getRecordHelper() {
        return recordHelper;
    }

    public RestrictionFactory getRestrictionFactory() {
        return restrictionFactory;
    }
}
