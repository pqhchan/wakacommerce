
package com.wakacommerce.openadmin.server.service;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FilterAndSortCriteria;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.dto.SectionCrumb;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceResponse;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface AdminEntityService {

    public PersistenceResponse getClassMetadata(PersistencePackageRequest request)
            throws ServiceException;

    public PersistenceResponse getRecords(PersistencePackageRequest request)
            throws ServiceException;

    public PersistenceResponse getRecord(PersistencePackageRequest request, String id, ClassMetadata cmd, boolean isCollectionRequest)
            throws ServiceException;

    public PersistenceResponse addEntity(EntityForm entityForm, String[] customCriteria, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse updateEntity(EntityForm entityForm, String[] customCriteria, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse removeEntity(EntityForm entityForm, String[] customCriteria, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse add(PersistencePackageRequest request) throws ServiceException;

    public PersistenceResponse add(PersistencePackageRequest request, boolean transactional) throws ServiceException;

    public PersistenceResponse update(PersistencePackageRequest request) throws ServiceException;

    public PersistenceResponse update(PersistencePackageRequest request, boolean transactional) throws ServiceException;

    public PersistenceResponse inspect(PersistencePackageRequest request) throws ServiceException;

    public PersistenceResponse remove(PersistencePackageRequest request) throws ServiceException;

    public PersistenceResponse remove(PersistencePackageRequest request, boolean transactional) throws ServiceException;

    public PersistenceResponse fetch(PersistencePackageRequest request) throws ServiceException;

    public PersistenceResponse getAdvancedCollectionRecord(ClassMetadata containingClassMetadata, Entity containingEntity,
            Property collectionProperty, String collectionItemId, List<SectionCrumb> sectionCrumb, String alternateId)
            throws ServiceException;

    public PersistenceResponse getRecordsForCollection(ClassMetadata containingClassMetadata, Entity containingEntity,
            Property collectionProperty, FilterAndSortCriteria[] fascs, Integer startIndex, Integer maxIndex, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse getRecordsForCollection(ClassMetadata containingClassMetadata, Entity containingEntity,
            Property collectionProperty, FilterAndSortCriteria[] fascs, Integer startIndex, Integer maxIndex, 
            String idValueOverride, List<SectionCrumb> sectionCrumb) throws ServiceException;

    public Map<String, DynamicResultSet> getRecordsForAllSubCollections(PersistencePackageRequest ppr, 
            Entity containingEntity, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse addSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
            Entity parentEntity, List<SectionCrumb> sectionCrumb)
            throws ServiceException, ClassNotFoundException;

    public PersistenceResponse updateSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
            Entity parentEntity, String collectionItemId, List<SectionCrumb> sectionCrumb)
            throws ServiceException, ClassNotFoundException;

    public PersistenceResponse updateSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
            Entity parentEntity, String collectionItemId, String alternateId, List<SectionCrumb> sectionCrumb)
            throws ServiceException, ClassNotFoundException;

    public PersistenceResponse removeSubCollectionEntity(ClassMetadata mainMetadata, Property field, Entity parentEntity, String itemId,
            String priorKey, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public PersistenceResponse removeSubCollectionEntity(ClassMetadata mainMetadata, Property field, Entity parentEntity,
            String itemId, String alternateId, String priorKey, List<SectionCrumb> sectionCrumb)
            throws ServiceException;

    public String getContextSpecificRelationshipId(ClassMetadata cmd, Entity entity, String propertyName);

    public String getIdProperty(ClassMetadata cmd) throws ServiceException;

    public String getForeignEntityName(String owningClass, String id);


}
