package com.wakacommerce.openadmin.web.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.util.BLCMapUtils;
import com.wakacommerce.common.util.TypedClosure;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.ClassTree;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.FilterAndSortCriteria;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.dto.SectionCrumb;
import com.wakacommerce.openadmin.dto.SortDirection;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.server.security.remote.SecurityVerifier;
import com.wakacommerce.openadmin.server.security.service.navigation.AdminNavigationService;
import com.wakacommerce.openadmin.server.service.AdminEntityService;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceResponse;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.DynamicEntityFormInfo;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.EntityFormValidator;
import com.wakacommerce.openadmin.web.form.entity.Field;
import com.wakacommerce.openadmin.web.form.entity.FieldGroup;
import com.wakacommerce.openadmin.web.form.entity.Tab;
import com.wakacommerce.openadmin.web.service.FormBuilderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public abstract class AdminAbstractController extends WakaAbstractController {
    
    public static final String FILTER_VALUE_SEPARATOR = "|";
    public static final String FILTER_VALUE_SEPARATOR_REGEX = "\\|";

    public static final String CURRENT_ADMIN_MODULE_ATTRIBUTE_NAME = "currentAdminModule";
    public static final String CURRENT_ADMIN_SECTION_ATTRIBUTE_NAME = "currentAdminSection";

    // ***********************
    // RESOURCE DECLARATIONS *
    // ***********************

    @Resource(name = "blAdminEntityService")
    protected AdminEntityService service;

    @Resource(name = "blFormBuilderService")
    protected FormBuilderService formService;
    
    @Resource(name = "blAdminNavigationService")
    protected AdminNavigationService adminNavigationService;
    
    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Resource(name = "blEntityFormValidator")
    protected EntityFormValidator entityFormValidator;
    
    @Resource(name="blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;

    @Deprecated
    @Resource(name = "blMainEntityActionsExtensionManager")
    protected MainEntityActionsExtensionManager mainEntityActionsExtensionManager;

    @Resource(name = "blAdminAbstractControllerExtensionManager")
    protected AdminAbstractControllerExtensionManager extensionManager;
    
    // *********************************************************
    // UNBOUND CONTROLLER METHODS (USED BY DIFFERENT SECTIONS) *
    // *********************************************************

    protected EntityForm getEntityForm(String sectionKey, String sectionClassName, String id) throws ServiceException {
        SectionCrumb sc = new SectionCrumb();
        sc.setSectionId(id);
        sc.setSectionIdentifier("structured-content/all");
        List<SectionCrumb> crumbs = new ArrayList<SectionCrumb>(1);
        crumbs.add(sc);

        PersistencePackageRequest ppr = getSectionPersistencePackageRequest(sectionClassName, crumbs, null);
        ClassMetadata cmd = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        Entity entity = service.getRecord(ppr, id, cmd, false).getDynamicResultSet().getRecords()[0];

        Map<String, DynamicResultSet> subRecordsMap = service.getRecordsForAllSubCollections(ppr, entity, crumbs);

        EntityForm entityForm = formService.createEntityForm(cmd, entity, subRecordsMap, crumbs);
        return entityForm;
    }

    protected String getDynamicForm(HttpServletRequest request, HttpServletResponse response, Model model,
            Map<String, String> pathVars,
            DynamicEntityFormInfo info) throws Exception {
        String sectionKey = getSectionKey(pathVars);
        EntityForm blankFormContainer = new EntityForm();
        EntityForm dynamicForm = getBlankDynamicFieldTemplateForm(info);

        blankFormContainer.putDynamicForm(info.getPropertyName(), dynamicForm);
        model.addAttribute("dynamicForm", dynamicForm);
        model.addAttribute("entityForm", blankFormContainer);
        model.addAttribute("dynamicPropertyName", info.getPropertyName());
        
        String reqUrl = request.getRequestURL().toString();
        reqUrl = reqUrl.substring(0, reqUrl.indexOf("/dynamicForm"));
        model.addAttribute("currentUrl", reqUrl);
        
        setModelAttributes(model, sectionKey);
        return "views/dynamicFormPartial";
    }
    
    // **********************************
    // HELPER METHODS FOR BUILDING DTOS *
    // **********************************

    protected ListGrid getCollectionListGrid(ClassMetadata mainMetadata, Entity entity, Property collectionProperty,
            MultiValueMap<String, String> requestParams, String sectionKey, PersistenceResponse persistenceResponse, List<SectionCrumb> sectionCrumbs)
            throws ServiceException {
        String idProperty = service.getIdProperty(mainMetadata);
        if (persistenceResponse != null && persistenceResponse.getAdditionalData().containsKey(PersistenceResponse.AdditionalData.CLONEID)) {
            entity.findProperty(idProperty).setValue((String) persistenceResponse.getAdditionalData().get(PersistenceResponse.AdditionalData.CLONEID));
        }
        DynamicResultSet drs = service.getRecordsForCollection(mainMetadata, entity, collectionProperty,
                getCriteria(requestParams), getStartIndex(requestParams), getMaxIndex(requestParams), sectionCrumbs).getDynamicResultSet();

        ListGrid listGrid = formService.buildCollectionListGrid(entity.findProperty(idProperty).getValue(), drs,
                collectionProperty, sectionKey, sectionCrumbs);
        listGrid.setListGridType(ListGrid.Type.INLINE);

        return listGrid;
    }

    protected ListGrid getCollectionListGrid(ClassMetadata mainMetadata, Entity entity, Property collectionProperty,
                MultiValueMap<String, String> requestParams, String sectionKey, List<SectionCrumb> sectionCrumbs)
                throws ServiceException {
        return getCollectionListGrid(mainMetadata, entity, collectionProperty, requestParams, sectionKey, null, sectionCrumbs);
    }

    protected EntityForm getBlankDynamicFieldTemplateForm(DynamicEntityFormInfo info) throws ServiceException {
        return getBlankDynamicFieldTemplateForm(info, null);
    }

    protected EntityForm getBlankDynamicFieldTemplateForm(DynamicEntityFormInfo info, EntityForm dynamicFormOverride) 
            throws ServiceException {
        // We need to inspect with the second custom criteria set to the id of
        // the desired structured content type
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(info.getCeilingClassName())
                .withSecurityCeilingEntityClassname(info.getSecurityCeilingClassName())
                .withCustomCriteria(new String[] { info.getCriteriaName(), null, info.getPropertyName(), info.getPropertyValue() });
        ClassMetadata cmd = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        
        EntityForm dynamicForm = formService.createEntityForm(cmd, null);
        dynamicForm.clearFieldsMap();

        if (dynamicFormOverride != null) {
            dynamicFormOverride.clearFieldsMap();
            Map<String, Field> fieldOverrides = dynamicFormOverride.getFields();
            for (Entry<String, Field> override : fieldOverrides.entrySet()) {
                if (dynamicForm.getFields().containsKey(override.getKey())) {
                    dynamicForm.getFields().get(override.getKey()).setValue(override.getValue().getValue());
                }
            }
        }
        
        // Set the specialized name for these fields - we need to handle them separately
        dynamicForm.clearFieldsMap();
        for (Tab tab : dynamicForm.getTabs()) {
            for (FieldGroup group : tab.getFieldGroups()) {
                for (Field field : group.getFields()) {
                    field.setName(info.getPropertyName() + DynamicEntityFormInfo.FIELD_SEPARATOR + field.getName());
                }
            }
        }

        //extensionManager.getProxy().modifyDynamicForm(dynamicForm, );

        return dynamicForm;
    }

    protected EntityForm getDynamicFieldTemplateForm(DynamicEntityFormInfo info, String entityId, EntityForm dynamicFormOverride) 
            throws ServiceException {
        // We need to inspect with the second custom criteria set to the id of
        // the desired structured content type
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(info.getCeilingClassName())
                .withSecurityCeilingEntityClassname(info.getSecurityCeilingClassName())
                .withCustomCriteria(new String[] { info.getCriteriaName(), entityId, info.getPropertyName(), info.getPropertyValue() });
        ClassMetadata cmd = service.getClassMetadata(ppr).getDynamicResultSet().getClassMetaData();
        
        // However, when we fetch, the second custom criteria needs to be the id
        // of this particular structured content entity
        ppr.setCustomCriteria(new String[] { info.getCriteriaName(), entityId });
        Entity entity = service.getRecord(ppr, info.getPropertyValue(), cmd, true).getDynamicResultSet().getRecords()[0];
        
        List<Field> fieldsToMove = new ArrayList<Field>();
        // override the results of the entity with the dynamic form passed in
        if (dynamicFormOverride != null) {
            dynamicFormOverride.clearFieldsMap();
            Map<String, Field> fieldOverrides = dynamicFormOverride.getFields();
            for (Entry<String, Field> override : fieldOverrides.entrySet()) {
                if (entity.getPMap().containsKey(override.getKey())) {
                    entity.getPMap().get(override.getKey()).setValue(override.getValue().getValue());
                } else {
                    fieldsToMove.add(override.getValue());
                }
            }
        }
        
        // Assemble the dynamic form for structured content type
        EntityForm dynamicForm = formService.createEntityForm(cmd, entity, null, null);
        
        for (Field field : fieldsToMove) {
            FieldMetadata fmd = cmd.getPMap().get(field.getName()).getMetadata();
            if (fmd instanceof BasicFieldMetadata) {
                BasicFieldMetadata bfmd = (BasicFieldMetadata) fmd;
                field.setFieldType(bfmd.getFieldType().toString());
                field.setFriendlyName(bfmd.getFriendlyName());
                field.setRequired(bfmd.getRequired());
            }
            dynamicForm.addField(field);
        }
        
        // Set the specialized name for these fields - we need to handle them separately
        dynamicForm.clearFieldsMap();
        for (Tab tab : dynamicForm.getTabs()) {
            for (FieldGroup group : tab.getFieldGroups()) {
                for (Field field : group.getFields()) {
                    field.setName(info.getPropertyName() + DynamicEntityFormInfo.FIELD_SEPARATOR + field.getName());
                }
            }
        }
        
        extensionManager.getProxy().modifyDynamicForm(dynamicForm, entityId);
    
        return dynamicForm;
    }

    protected void extractDynamicFormFields(EntityForm entityForm) {
        Map<String, Field> dynamicFields = new HashMap<String, Field>();
        
        // Find all of the dynamic form fields
        for (Entry<String, Field> entry : entityForm.getFields().entrySet()) {
            if (entry.getKey().contains(DynamicEntityFormInfo.FIELD_SEPARATOR)) { 
                dynamicFields.put(entry.getKey(), entry.getValue());
            }
        }
        
        // Remove the dynamic form fields from the main entity - they are persisted separately
        for (Entry<String, Field> entry : dynamicFields.entrySet()) {
            entityForm.removeField(entry.getKey());
        }
        
        // Create the entity form for the dynamic form, as it needs to be persisted separately
        for (Entry<String, Field> entry : dynamicFields.entrySet()) {
            String[] fieldName = entry.getKey().split("\\" + DynamicEntityFormInfo.FIELD_SEPARATOR);
            DynamicEntityFormInfo info = entityForm.getDynamicFormInfo(fieldName[0]);
                    
            EntityForm dynamicForm = entityForm.getDynamicForm(fieldName[0]);
            if (dynamicForm == null) {
                dynamicForm = new EntityForm();
                dynamicForm.setCeilingEntityClassname(info.getCeilingClassName());
                entityForm.putDynamicForm(fieldName[0], dynamicForm);
            }
            
            entry.getValue().setName(fieldName[1]);
            dynamicForm.addField(entry.getValue());
        }
    }

    
    // ***********************************************
    // HELPER METHODS FOR SECTION-SPECIFIC OVERRIDES *
    // ***********************************************

    protected String getSectionKey(Map<String, String> pathVars) {
        return pathVars.get("sectionKey");
    }

    protected FilterAndSortCriteria[] getCriteria(Map<String, List<String>> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        
        List<FilterAndSortCriteria> result = new ArrayList<FilterAndSortCriteria>();
        for (Entry<String, List<String>> entry : requestParams.entrySet()) {
            if (!entry.getKey().equals(FilterAndSortCriteria.SORT_PROPERTY_PARAMETER) &&
                    !entry.getKey().equals(FilterAndSortCriteria.SORT_DIRECTION_PARAMETER)) {
                List<String> values = entry.getValue();
                List<String> collapsedValues = new ArrayList<String>();
                for (String value : values) {
                    if (value.contains(FILTER_VALUE_SEPARATOR)) {
                        String[] vs = value.split(FILTER_VALUE_SEPARATOR_REGEX);
                        for (String v : vs) {
                            collapsedValues.add(v);
                        }
                    } else {
                        collapsedValues.add(value);
                    }
                }
                
                FilterAndSortCriteria fasCriteria = new FilterAndSortCriteria(entry.getKey(), collapsedValues);
                result.add(fasCriteria);
            }
        }

        List<String> sortProperties = getSortPropertyNames(requestParams);
        List<String> sortDirections = getSortDirections(requestParams);
        if (CollectionUtils.isNotEmpty(sortProperties)) {
            //set up a map to determine if there is already some criteria set for the sort property
            Map<String, FilterAndSortCriteria> fasMap = BLCMapUtils.keyedMap(result, new TypedClosure<String, FilterAndSortCriteria>() {
    
                @Override
                public String getKey(FilterAndSortCriteria value) {
                    return value.getPropertyId();
                }
            });
            for (int i = 0; i < sortProperties.size(); i++) {
                boolean sortAscending = SortDirection.ASCENDING.toString().equals(sortDirections.get(i));
                FilterAndSortCriteria propertyCriteria = fasMap.get(sortProperties.get(i));
                //If there is already criteria for this property, attach the sort to that. Otherwise, create some new
                //FilterAndSortCriteria for the sort
                if (propertyCriteria != null) {
                    propertyCriteria.setSortAscending(sortAscending);
                } else {
                    FilterAndSortCriteria fasc = new FilterAndSortCriteria(sortProperties.get(i));
                    fasc.setSortAscending(sortAscending);
                    result.add(fasc);
                }
            }
        }
        
        return result.toArray(new FilterAndSortCriteria[result.size()]);
    }

    protected List<String> getSortDirections(Map<String, List<String>> requestParams) {
        List<String> sortTypes = requestParams.get(FilterAndSortCriteria.SORT_DIRECTION_PARAMETER);
        return sortTypes;
    }

    protected List<String> getSortPropertyNames(Map<String, List<String>> requestParams) {
        return requestParams.get(FilterAndSortCriteria.SORT_PROPERTY_PARAMETER);
    }

    protected String getClassNameForSection(String sectionKey) {
        AdminSection section = adminNavigationService.findAdminSectionByURI("/" + sectionKey);
        
        ExtensionResultHolder erh = new ExtensionResultHolder();
        extensionManager.getProxy().overrideClassNameForSection(erh, sectionKey, section);
        if (erh.getContextMap().get(AbstractAdminAbstractControllerExtensionHandler.NEW_CLASS_NAME) != null) {
            return (String) erh.getContextMap().get(AbstractAdminAbstractControllerExtensionHandler.NEW_CLASS_NAME); 
        }
        
        return (section == null) ? sectionKey : section.getCeilingEntity();
    }

    protected List<ClassTree> getAddEntityTypes(ClassTree classTree) {
        return classTree.getCollapsedClassTrees();
    }

    protected String getDefaultEntityType() {
        return null;
    }

    protected String[] getSectionCustomCriteria() {
        return null;
    }

    protected void attachSectionSpecificInfo(PersistencePackageRequest ppr) {
        
    }

    protected void modifyEntityForm(EntityForm entityForm, Map<String, String> pathVars) {
        
    }

    protected void modifyAddEntityForm(EntityForm entityForm, Map<String, String> pathVars) {
        
    }

    protected void attachSectionSpecificInfo(PersistencePackageRequest ppr, Map<String, String> pathVars) {
        attachSectionSpecificInfo(ppr);
    }

    protected Integer getStartIndex(Map<String, List<String>> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        
        List<String> startIndex = requestParams.get(FilterAndSortCriteria.START_INDEX_PARAMETER);
        return CollectionUtils.isEmpty(startIndex) ? null : Integer.parseInt(startIndex.get(0));
    }

    protected Integer getMaxIndex(Map<String, List<String>> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        
        List<String> maxIndex = requestParams.get(FilterAndSortCriteria.MAX_INDEX_PARAMETER);
        return CollectionUtils.isEmpty(maxIndex) ? null : Integer.parseInt(maxIndex.get(0));
    }
    
    // ************************
    // GENERIC HELPER METHODS *
    // ************************

    protected void setModelAttributes(Model model, String sectionKey) {
        AdminSection section = adminNavigationService.findAdminSectionByURI("/" + sectionKey);

        if (section != null) {
            model.addAttribute("sectionKey", sectionKey);
            model.addAttribute(CURRENT_ADMIN_MODULE_ATTRIBUTE_NAME, section.getModule());
            model.addAttribute(CURRENT_ADMIN_SECTION_ATTRIBUTE_NAME, section);
        }
        
        extensionManager.getProxy().setAdditionalModelAttributes(model, sectionKey);
    }

    protected PersistencePackageRequest getSectionPersistencePackageRequest(String sectionClassName, List<SectionCrumb> sectionCrumbs) {
        return getSectionPersistencePackageRequest(sectionClassName, sectionCrumbs, null);
    }

    protected PersistencePackageRequest getSectionPersistencePackageRequest(String sectionClassName, 
            List<SectionCrumb> sectionCrumbs, Map<String, String> pathVars) {
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(sectionClassName)
                .withCustomCriteria(getSectionCustomCriteria())
                .withSectionCrumbs(sectionCrumbs);

        attachSectionSpecificInfo(ppr, pathVars);
        
        return ppr;
    }

    protected PersistencePackageRequest getSectionPersistencePackageRequest(String sectionClassName, 
            MultiValueMap<String, String> requestParams, List<SectionCrumb> sectionCrumbs) {
        return getSectionPersistencePackageRequest(sectionClassName, requestParams, sectionCrumbs, null);
    }

    protected PersistencePackageRequest getSectionPersistencePackageRequest(String sectionClassName, 
            MultiValueMap<String, String> requestParams, List<SectionCrumb> sectionCrumbs, Map<String, String> pathVars) {
        FilterAndSortCriteria[] fascs = getCriteria(requestParams);
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(sectionClassName)
                .withCustomCriteria(getSectionCustomCriteria())
                .withFilterAndSortCriteria(fascs)
                .withStartIndex(getStartIndex(requestParams))
                .withMaxIndex(getMaxIndex(requestParams))
                .withSectionCrumbs(sectionCrumbs);

        attachSectionSpecificInfo(ppr, pathVars);

        return ppr;
    }

    protected List<SectionCrumb> getSectionCrumbs(HttpServletRequest request, String currentSection, String currentSectionId) {
        String crumbs = request.getParameter("sectionCrumbs");
        List<SectionCrumb> myCrumbs = new ArrayList<SectionCrumb>();
        if (!StringUtils.isEmpty(crumbs)) {
            String[] crumbParts = crumbs.split(",");
            for (String part : crumbParts) {
                SectionCrumb crumb = new SectionCrumb();
                String[] crumbPieces = part.split("--");
                crumb.setSectionIdentifier(crumbPieces[0]);
                crumb.setSectionId(crumbPieces[1]);
                if (!myCrumbs.contains(crumb)) {
                    myCrumbs.add(crumb);
                }
            }
        }
        if (currentSection != null && currentSectionId != null) {
            SectionCrumb crumb = new SectionCrumb();
            if (currentSection.startsWith("/")) {
                currentSection = currentSection.substring(1, currentSection.length());
            }
            crumb.setSectionIdentifier(currentSection);
            crumb.setSectionId(currentSectionId);
            if (!myCrumbs.contains(crumb)) {
                myCrumbs.add(crumb);
            }
        }
        return myCrumbs;
    }
}
