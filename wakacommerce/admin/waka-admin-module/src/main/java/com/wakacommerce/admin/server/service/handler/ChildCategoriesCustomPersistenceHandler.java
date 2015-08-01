
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.presentation.client.PersistencePerspectiveItemType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryImpl;
import com.wakacommerce.core.catalog.domain.CategoryXref;
import com.wakacommerce.core.catalog.domain.CategoryXrefImpl;
import com.wakacommerce.openadmin.dto.AdornedTargetList;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

/**
 * 
 */
@Component("blChildCategoriesCustomPersistenceHandler")
public class ChildCategoriesCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        return (!ArrayUtils.isEmpty(persistencePackage.getCustomCriteria()) && persistencePackage.getCustomCriteria()[0].equals("blcAllParentCategories"));
    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        AdornedTargetList adornedTargetList = (AdornedTargetList) persistencePackage.getPersistencePerspective().getPersistencePerspectiveItems().get(PersistencePerspectiveItemType.ADORNEDTARGETLIST);
        String targetPath = adornedTargetList.getTargetObjectPath() + "." + adornedTargetList.getTargetIdProperty();
        String linkedPath = adornedTargetList.getLinkedObjectPath() + "." + adornedTargetList.getLinkedIdProperty();
        
        Long parentId = Long.parseLong(persistencePackage.getEntity().findProperty(linkedPath).getValue());
        Long childId = Long.parseLong(persistencePackage.getEntity().findProperty(targetPath).getValue());
        
        Category parent = (Category) dynamicEntityDao.retrieve(CategoryImpl.class, parentId);
        Category child = (Category) dynamicEntityDao.retrieve(CategoryImpl.class, childId);

        CategoryXref categoryXref = new CategoryXrefImpl();
        categoryXref.setSubCategory(child);
        categoryXref.setCategory(parent);
        if (parent.getAllChildCategoryXrefs().contains(categoryXref)) {
            throw new ServiceException("Add unsuccessful. Cannot add a duplicate child category.");
        }

        checkParents(child, parent);
        
        return helper.getCompatibleModule(OperationType.ADORNEDTARGETLIST).add(persistencePackage);
    }
    
    protected void checkParents(Category child, Category parent) throws ServiceException {
        if (child.getId().equals(parent.getId())) {
            throw new ServiceException("Add unsuccessful. Cannot add a category to itself.");
        }
        for (CategoryXref category : parent.getAllParentCategoryXrefs()) {
            if (!CollectionUtils.isEmpty(category.getCategory().getAllParentCategoryXrefs())) {
                checkParents(child, category.getCategory());
            }
        }
    }
 }
