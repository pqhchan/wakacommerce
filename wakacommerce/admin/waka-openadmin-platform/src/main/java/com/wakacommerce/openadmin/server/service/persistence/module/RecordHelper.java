
package com.wakacommerce.openadmin.server.service.persistence.module;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.EntityResult;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.service.ValidationException;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory;
import com.wakacommerce.openadmin.server.service.persistence.validation.EntityValidatorService;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface RecordHelper extends DataFormatProvider {

    public List<FilterMapping> getFilterMappings(PersistencePerspective persistencePerspective, CriteriaTransferObject cto,
                                                 String ceilingEntityFullyQualifiedClassname,
                                                 Map<String, FieldMetadata> mergedProperties);

    public List<FilterMapping> getFilterMappings(PersistencePerspective persistencePerspective, CriteriaTransferObject cto,
                                                     String ceilingEntityFullyQualifiedClassname,
                                                     Map<String, FieldMetadata> mergedUnfilteredProperties,
                                                     RestrictionFactory customRestrictionFactory);

    public Entity[] getRecords(Map<String, FieldMetadata> primaryMergedProperties, List<? extends Serializable> records, Map<String, FieldMetadata> alternateMergedProperties, String pathToTargetObject);

    public Entity[] getRecords(Map<String, FieldMetadata> primaryMergedProperties, List<? extends Serializable> records);
    
    public Entity[] getRecords(Class<?> ceilingEntityClass, PersistencePerspective persistencePerspective, List<? extends Serializable> records);
    
    public Entity getRecord(Map<String, FieldMetadata> primaryMergedProperties, Serializable record, Map<String, FieldMetadata> alternateMergedProperties, String pathToTargetObject);
    
    public Entity getRecord(Class<?> ceilingEntityClass, PersistencePerspective persistencePerspective, Serializable record);

    public Serializable createPopulatedInstance(Serializable instance, Entity entity, 
            Map<String, FieldMetadata> mergedProperties, Boolean setId, Boolean validateUnsubmittedProperties) throws ValidationException;

    public Serializable createPopulatedInstance(Serializable instance, Entity entity, 
            Map<String, FieldMetadata> unfilteredProperties, Boolean setId) throws ValidationException;
    
    public Object getPrimaryKey(Entity entity, Map<String, FieldMetadata> mergedProperties);
    
    public Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective);
    
    public FieldManager getFieldManager();

    public PersistenceModule getCompatibleModule(OperationType operationType);

    public boolean validate(Entity entity, Serializable populatedInstance, Map<String, FieldMetadata> mergedProperties, boolean validateUnsubmittedProperties);

    public boolean validate(Entity entity, Serializable populatedInstance, Map<String, FieldMetadata> mergedProperties);

    public Integer getTotalRecords(String ceilingEntity, List<FilterMapping> filterMappings);

    public Serializable getMaxValue(String ceilingEntity, List<FilterMapping> filterMappings, String maxField);

    public List<Serializable> getPersistentRecords(String ceilingEntity, List<FilterMapping> filterMappings, Integer firstResult, Integer maxResults);

    public EntityResult update(PersistencePackage persistencePackage, boolean includeRealEntityObject) throws ServiceException;

    public EntityResult add(PersistencePackage persistencePackage, boolean includeRealEntityObject) throws ServiceException;

    public String getStringValueFromGetter(Serializable instance, String propertyName)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
