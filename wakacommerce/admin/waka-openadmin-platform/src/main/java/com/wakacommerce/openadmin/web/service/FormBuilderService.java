
package com.wakacommerce.openadmin.web.service;

import org.springframework.validation.BindingResult;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.AdornedTargetCollectionMetadata;
import com.wakacommerce.openadmin.dto.AdornedTargetList;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.MapMetadata;
import com.wakacommerce.openadmin.dto.MapStructure;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.dto.SectionCrumb;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface FormBuilderService {

    public ListGrid buildMainListGrid(DynamicResultSet drs, ClassMetadata cmd, String sectionKey, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public ListGrid buildCollectionListGrid(String containingEntityId, DynamicResultSet drs, Property field, String sectionKey, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public String extractDefaultValueFromFieldData(String fieldType, BasicFieldMetadata fmd);

    public void removeNonApplicableFields(ClassMetadata cmd, EntityForm entityForm, String entityType);

    public EntityForm createEntityForm(ClassMetadata cmd, List<SectionCrumb> sectionCrumbs) throws ServiceException;

    public void populateEntityForm(ClassMetadata cmd, EntityForm ef, List<SectionCrumb> sectionCrumbs) throws ServiceException;

    public EntityForm createEntityForm(ClassMetadata cmd, Entity entity, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public void populateEntityForm(ClassMetadata cmd, Entity entity, EntityForm ef, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public void populateEntityFormFieldValues(ClassMetadata cmd, Entity entity, EntityForm ef);

    public EntityForm createEntityForm(ClassMetadata cmd, Entity entity, Map<String, DynamicResultSet> collectionRecords, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public void populateEntityForm(ClassMetadata cmd, Entity entity, Map<String, DynamicResultSet> collectionRecords, EntityForm entityForm, List<SectionCrumb> sectionCrumbs)
            throws ServiceException;

    public void populateEntityFormFields(EntityForm ef, Entity entity);

    public void populateEntityFormFields(EntityForm ef, Entity entity, boolean populateType, boolean populateId);

    public void populateAdornedEntityFormFields(EntityForm ef, Entity entity, AdornedTargetList adornedList);

    public void populateMapEntityFormFields(EntityForm ef, Entity entity);

    public EntityForm buildAdornedListForm(AdornedTargetCollectionMetadata adornedMd, AdornedTargetList adornedList,
            String parentId)
            throws ServiceException;

    public EntityForm buildAdornedListForm(AdornedTargetCollectionMetadata adornedMd, AdornedTargetList adornedList,
            String parentId, EntityForm ef)
            throws ServiceException;

    public EntityForm buildMapForm(MapMetadata mapMd, MapStructure mapStructure, ClassMetadata cmd, String parentId)
            throws ServiceException;

    public EntityForm buildMapForm(MapMetadata mapMd, final MapStructure mapStructure, ClassMetadata cmd, String parentId, EntityForm ef)
            throws ServiceException;
}
