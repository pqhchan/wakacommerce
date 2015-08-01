
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.admin.server.service.extension.CategoryCustomPersistenceHandlerExtensionManager;
import com.wakacommerce.common.exception.ExceptionHelper;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.service.ParentCategoryLegacyModeService;
import com.wakacommerce.common.service.ParentCategoryLegacyModeServiceImpl;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryImpl;
import com.wakacommerce.core.catalog.domain.CategoryXref;
import com.wakacommerce.core.catalog.domain.CategoryXrefImpl;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.InspectHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 
 *  
 *
 */
@Component("blCategoryCustomPersistenceHandler")
public class CategoryCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
    
    private static final Log LOG = LogFactory.getLog(CategoryCustomPersistenceHandler.class);

    @Resource(name = "blCategoryCustomPersistenceHandlerExtensionManager")
    protected CategoryCustomPersistenceHandlerExtensionManager extensionManager;

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        String[] customCriteria = persistencePackage.getCustomCriteria();
        return !ArrayUtils.isEmpty(customCriteria) && "categoryDirectEdit".equals(customCriteria[0]) && Category.class.getName().equals(ceilingEntityFullyQualifiedClassname);
    }

    @Override
    public Boolean canHandleInspect(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, InspectHelper helper) throws ServiceException {
        Map<String, FieldMetadata> md = getMetadata(persistencePackage, helper);

        if (!isDefaultCategoryLegacyMode()) {
            md.remove("allParentCategoryXrefs");

            BasicFieldMetadata defaultCategory = ((BasicFieldMetadata) md.get("defaultParentCategory"));
            defaultCategory.setFriendlyName("CategoryImpl_ParentCategory");
        }

        return getResultSet(persistencePackage, helper, md);
    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity  = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Category adminInstance = (Category) Class.forName(entity.getType()[0]).newInstance();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Category.class.getName(), persistencePerspective);
            adminInstance = (Category) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            adminInstance = dynamicEntityDao.merge(adminInstance);
            boolean handled = false;
            if (extensionManager != null) {
                ExtensionResultStatusType result = extensionManager.getProxy().manageParentCategoryForAdd(persistencePackage, adminInstance);
                handled = ExtensionResultStatusType.NOT_HANDLED != result;
            }
            if (!handled) {
                setupXref(adminInstance);
            }
            adminInstance = dynamicEntityDao.merge(adminInstance);

            return helper.getRecord(adminProperties, adminInstance, null, null);
        } catch (Exception e) {
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }

    @Override
    public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Category.class.getName(), persistencePerspective);
            Object primaryKey = helper.getPrimaryKey(entity, adminProperties);
            Category adminInstance = (Category) dynamicEntityDao.retrieve(Class.forName(entity.getType()[0]), primaryKey);
            CategoryXref oldDefault = getCurrentDefaultXref(adminInstance);
            adminInstance = (Category) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            adminInstance = dynamicEntityDao.merge(adminInstance);
            boolean handled = false;
            if (extensionManager != null) {
                ExtensionResultStatusType result = extensionManager.getProxy().manageParentCategoryForUpdate(persistencePackage, adminInstance);
                handled = ExtensionResultStatusType.NOT_HANDLED != result;
            }
            if (!handled) {
                setupXref(adminInstance);
                removeOldDefault(adminInstance, oldDefault, entity);
            }

            return helper.getRecord(adminProperties, adminInstance, null, null);
        } catch (Exception e) {
            throw new ServiceException("Unable to update entity for " + entity.getType()[0], e);
        }
    }

    protected Boolean isDefaultCategoryLegacyMode() {
        ParentCategoryLegacyModeService legacyModeService = ParentCategoryLegacyModeServiceImpl.getLegacyModeService();
        if (legacyModeService != null) {
            return legacyModeService.isLegacyMode();
        }
        return false;
    }

    protected Category getExistingDefaultCategory(Category category) {
        //Make sure we get the actual field value - not something manipulated in the getter
        Category parentCategory;
        try {
            Field defaultCategory = CategoryImpl.class.getDeclaredField("defaultParentCategory");
            defaultCategory.setAccessible(true);
            parentCategory = (Category) defaultCategory.get(category);
        } catch (NoSuchFieldException e) {
            throw ExceptionHelper.refineException(e);
        } catch (IllegalAccessException e) {
            throw ExceptionHelper.refineException(e);
        }
        return parentCategory;
    }

    protected void removeOldDefault(Category adminInstance, CategoryXref oldDefault, Entity entity) {
        if (!isDefaultCategoryLegacyMode()) {
            if (entity.findProperty("defaultParentCategory") != null && StringUtils.isEmpty(entity.findProperty("defaultParentCategory").getValue())) {
                adminInstance.setParentCategory(null);
            }
            CategoryXref newDefault = getCurrentDefaultXref(adminInstance);
            if (oldDefault != null && !oldDefault.equals(newDefault)) {
                adminInstance.getAllParentCategoryXrefs().remove(oldDefault);
            }
        }
    }

    protected void setupXref(Category adminInstance) {
        if (isDefaultCategoryLegacyMode()) {
            CategoryXref categoryXref = new CategoryXrefImpl();
            categoryXref.setCategory(getExistingDefaultCategory(adminInstance));
            categoryXref.setSubCategory(adminInstance);
            if (!adminInstance.getAllParentCategoryXrefs().contains(categoryXref) && categoryXref.getCategory() != null) {
                adminInstance.getAllParentCategoryXrefs().add(categoryXref);
            }
        }
    }

    protected CategoryXref getCurrentDefaultXref(Category category) {
        CategoryXref currentDefault = null;
        List<CategoryXref> xrefs = category.getAllParentCategoryXrefs();
        if (!CollectionUtils.isEmpty(xrefs)) {
            for (CategoryXref xref : xrefs) {
                if (xref.getCategory().isActive() && xref.getDefaultReference() != null && xref.getDefaultReference()) {
                    currentDefault = xref;
                    break;
                }
            }
        }
        return currentDefault;
    }

}
