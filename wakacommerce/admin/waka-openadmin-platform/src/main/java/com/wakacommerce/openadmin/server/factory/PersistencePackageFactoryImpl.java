
package com.wakacommerce.openadmin.server.factory;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.exception.ExceptionHelper;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.presentation.client.PersistencePerspectiveItemType;
import com.wakacommerce.common.util.dao.DynamicDaoHelper;
import com.wakacommerce.common.util.dao.DynamicDaoHelperImpl;
import com.wakacommerce.openadmin.dto.OperationTypes;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.dto.SectionCrumb;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.server.security.service.navigation.AdminNavigationService;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @ hui
 */
@Service("blPersistencePackageFactory")
public class PersistencePackageFactoryImpl implements PersistencePackageFactory {

    @Resource(name = "blAdminNavigationService")
    protected AdminNavigationService adminNavigationService;

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    protected DynamicDaoHelper dynamicDaoHelper = new DynamicDaoHelperImpl();

    @Override
    public PersistencePackage create(PersistencePackageRequest request) {
        PersistencePerspective persistencePerspective = new PersistencePerspective();

        persistencePerspective.setAdditionalForeignKeys(request.getAdditionalForeignKeys());
        persistencePerspective.setAdditionalNonPersistentProperties(new String[] {});
        
        if (request.getForeignKey() != null) {
            persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.FOREIGNKEY, 
                    request.getForeignKey());
        }

        switch (request.getType()) {
            case STANDARD:
                persistencePerspective.setOperationTypes(getDefaultOperationTypes());
                break;

            case ADORNED:
                if (request.getAdornedList() == null) {
                    throw new IllegalArgumentException("ADORNED type requires the adornedList to be set");
                }

                persistencePerspective.setOperationTypes(getOperationTypes(OperationType.ADORNEDTARGETLIST));
                persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.ADORNEDTARGETLIST,
                        request.getAdornedList());
                break;

            case MAP:
                if (request.getMapStructure() == null) {
                    throw new IllegalArgumentException("MAP type requires the mapStructure to be set");
                }

                persistencePerspective.setOperationTypes(getOperationTypes(OperationType.MAP));
                persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.MAPSTRUCTURE,
                        request.getMapStructure());
                break;
        }

        if (request.getOperationTypesOverride() != null) {
            persistencePerspective.setOperationTypes(request.getOperationTypesOverride());
        }

        PersistencePackage pp = new PersistencePackage();
        pp.setCeilingEntityFullyQualifiedClassname(request.getCeilingEntityClassname());
        if (!StringUtils.isEmpty(request.getSecurityCeilingEntityClassname())) {
            pp.setSecurityCeilingEntityFullyQualifiedClassname(request.getSecurityCeilingEntityClassname());
        }
        if (!ArrayUtils.isEmpty(request.getSectionCrumbs())) {
            SectionCrumb[] converted = new SectionCrumb[request.getSectionCrumbs().length];
            int index = 0;
            for (SectionCrumb crumb : request.getSectionCrumbs()) {
                SectionCrumb temp = new SectionCrumb();
                String originalSectionIdentifier = crumb.getSectionIdentifier();
                String sectionAsClassName;
                try {
                    sectionAsClassName = getClassNameForSection(crumb.getSectionIdentifier());
                } catch (Exception e) {
                    sectionAsClassName = request.getCeilingEntityClassname();
                }
                if (sectionAsClassName != null && !sectionAsClassName.equals(originalSectionIdentifier)) {
                    temp.setOriginalSectionIdentifier(originalSectionIdentifier);
                }
                temp.setSectionIdentifier(sectionAsClassName);

                temp.setSectionId(crumb.getSectionId());
                converted[index] = temp;
                index++;
            }
            pp.setSectionCrumbs(converted);
        }
        pp.setSectionEntityField(request.getSectionEntityField());
        pp.setFetchTypeFullyQualifiedClassname(null);
        pp.setPersistencePerspective(persistencePerspective);
        pp.setCustomCriteria(request.getCustomCriteria());
        pp.setCsrfToken(null);
        pp.setRequestingEntityName(request.getRequestingEntityName());
        pp.setValidateUnsubmittedProperties(request.isValidateUnsubmittedProperties());


        if (request.getEntity() != null) {
            pp.setEntity(request.getEntity());
        }

        for (Map.Entry<String, PersistencePackageRequest> subRequest : request.getSubRequests().entrySet()) {
            pp.getSubPackages().put(subRequest.getKey(), create(subRequest.getValue()));
        }

        return pp;
    }

    protected OperationTypes getDefaultOperationTypes() {
        OperationTypes operationTypes = new OperationTypes();
        operationTypes.setFetchType(OperationType.BASIC);
        operationTypes.setRemoveType(OperationType.BASIC);
        operationTypes.setAddType(OperationType.BASIC);
        operationTypes.setUpdateType(OperationType.BASIC);
        operationTypes.setInspectType(OperationType.BASIC);
        return operationTypes;
    }
    
    protected OperationTypes getOperationTypes(OperationType nonInspectOperationType) {
        OperationTypes operationTypes = new OperationTypes();
        operationTypes.setFetchType(nonInspectOperationType);
        operationTypes.setRemoveType(nonInspectOperationType);
        operationTypes.setAddType(nonInspectOperationType);
        operationTypes.setUpdateType(nonInspectOperationType);
        operationTypes.setInspectType(OperationType.BASIC);
        return operationTypes;
    }

    protected String getClassNameForSection(String sectionKey) {
        try {
            AdminSection section = adminNavigationService.findAdminSectionByURI("/" + sectionKey);
            String className = (section == null) ? sectionKey : section.getCeilingEntity();
            Class<?>[] entities = dynamicDaoHelper.getAllPolymorphicEntitiesFromCeiling(Class.forName(className), em.unwrap(Session.class).getSessionFactory(), true, true);
            return entities[entities.length - 1].getName();
        } catch (ClassNotFoundException e) {
            throw ExceptionHelper.refineException(RuntimeException.class, RuntimeException.class, e);
        }
    }
}
