
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.admin.server.service.extension.ProductCustomPersistenceHandlerExtensionManager;
import com.wakacommerce.common.exception.ExceptionHelper;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.service.ParentCategoryLegacyModeService;
import com.wakacommerce.common.service.ParentCategoryLegacyModeServiceImpl;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryProductXref;
import com.wakacommerce.core.catalog.domain.CategoryProductXrefImpl;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.ProductImpl;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.catalog.service.type.ProductBundlePricingModelType;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.ValidationException;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.EmptyFilterValues;
import com.wakacommerce.openadmin.server.service.persistence.module.InspectHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.Restriction;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * 
 */
@Component("blProductCustomPersistenceHandler")
public class ProductCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Resource(name = "blProductCustomPersistenceHandlerExtensionManager")
    protected ProductCustomPersistenceHandlerExtensionManager extensionManager;

    private static final Log LOG = LogFactory.getLog(ProductCustomPersistenceHandler.class);

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        String[] customCriteria = persistencePackage.getCustomCriteria();
        return !ArrayUtils.isEmpty(customCriteria) && "productDirectEdit".equals(customCriteria[0]) && Product.class.getName().equals(ceilingEntityFullyQualifiedClassname);
    }

    @Override
    public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public Boolean canHandleRemove(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public Boolean canHandleFetch(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public Boolean canHandleInspect(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, InspectHelper helper) throws ServiceException {
        Map<String, FieldMetadata> md = getMetadata(persistencePackage, helper);

        if (!isDefaultCategoryLegacyMode()) {
            md.remove("allParentCategoryXrefs");

            BasicFieldMetadata defaultCategory = ((BasicFieldMetadata) md.get("defaultCategory"));
            defaultCategory.setFriendlyName("ProductImpl_Parent_Category");
        }

        return getResultSet(persistencePackage, helper, md);
    }

    @Override
    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto, DynamicEntityDao
            dynamicEntityDao, RecordHelper helper) throws ServiceException {
        cto.getNonCountAdditionalFilterMappings().add(new FilterMapping()
                .withDirectFilterValues(new EmptyFilterValues())
                .withRestriction(new Restriction()
                                .withPredicateProvider(new PredicateProvider() {
                                    public Predicate buildPredicate(CriteriaBuilder builder,
                                                                    FieldPathBuilder fieldPathBuilder, From root,
                                                                    String ceilingEntity,
                                                                    String fullPropertyName, Path explicitPath,
                                                                    List directValues) {
                                        root.fetch("defaultSku", JoinType.LEFT);
                                        root.fetch("defaultCategory", JoinType.LEFT);
                                        return null;
                                    }
                                })
                ));
        return helper.getCompatibleModule(OperationType.BASIC).fetch(persistencePackage, cto);
    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity  = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Product adminInstance = (Product) Class.forName(entity.getType()[0]).newInstance();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Product.class.getName(), persistencePerspective);

            if (adminInstance instanceof ProductBundle) {
                removeBundleFieldRestrictions((ProductBundle)adminInstance, adminProperties, entity);
            }
            
            adminInstance = (Product) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            adminInstance = dynamicEntityDao.merge(adminInstance);
            boolean handled = false;
            if (extensionManager != null) {
                ExtensionResultStatusType result = extensionManager.getProxy().manageParentCategoryForAdd(persistencePackage, adminInstance);
                handled = ExtensionResultStatusType.NOT_HANDLED != result;
            }
            if (!handled) {
                setupXref(adminInstance);
            }
            
            //Since none of the Sku fields are required, it's possible that the user did not fill out
            //any Sku fields, and thus a Sku would not be created. Product still needs a default Sku so instantiate one
            if (adminInstance.getDefaultSku() == null) {
                Sku newSku = catalogService.createSku();
                dynamicEntityDao.persist(newSku);
                adminInstance.setDefaultSku(newSku);
                adminInstance = dynamicEntityDao.merge(adminInstance);
            }

            //also set the default product for the Sku
            adminInstance.getDefaultSku().setDefaultProduct(adminInstance);
            dynamicEntityDao.merge(adminInstance.getDefaultSku());
            
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

            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Product.class.getName(), persistencePerspective);
            BasicFieldMetadata defaultCategory = ((BasicFieldMetadata) adminProperties.get("defaultCategory"));
            defaultCategory.setFriendlyName("ProductImpl_Parent_Category");
            if (entity.findProperty("defaultCategory") != null && !StringUtils.isEmpty(entity.findProperty("defaultCategory").getValue())) {
                //Change the inherited type so that this property is disconnected from the entity and validation is temporarily skipped.
                //This is useful when the defaultCategory was previously completely empty for whatever reason. Without this, such
                //a case would fail the validation, even though the property was specified in the submission.
                defaultCategory.setInheritedFromType(String.class.getName());
            }

            Object primaryKey = helper.getPrimaryKey(entity, adminProperties);
            Product adminInstance = (Product) dynamicEntityDao.retrieve(Class.forName(entity.getType()[0]), primaryKey);
            if (adminInstance instanceof ProductBundle) {
                removeBundleFieldRestrictions((ProductBundle)adminInstance, adminProperties, entity);
            }

            CategoryProductXref oldDefault = getCurrentDefaultXref(adminInstance);
            adminInstance = (Product) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            adminInstance = dynamicEntityDao.merge(adminInstance);
            boolean handled = false;
            if (extensionManager != null) {
                ExtensionResultStatusType result = extensionManager.getProxy().manageParentCategoryForUpdate
                        (persistencePackage, adminInstance);
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

    @Override
    public void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        helper.getCompatibleModule(OperationType.BASIC).remove(persistencePackage);
    }

    /**
     * If the pricing model is of type item_sum, that property should not be required
     * @param adminInstance
     * @param adminProperties
     * @param entity
     */
    protected void removeBundleFieldRestrictions(ProductBundle adminInstance, Map<String, FieldMetadata> adminProperties, Entity entity) {
        //no required validation for product bundles
        if (entity.getPMap().get("pricingModel") != null) {
            if (ProductBundlePricingModelType.ITEM_SUM.getType().equals(entity.getPMap().get("pricingModel").getValue())) {
                ((BasicFieldMetadata)adminProperties.get("defaultSku.retailPrice")).setRequiredOverride(false);
            }
        }
    }

    protected Boolean isDefaultCategoryLegacyMode() {
        ParentCategoryLegacyModeService legacyModeService = ParentCategoryLegacyModeServiceImpl.getLegacyModeService();
        if (legacyModeService != null) {
            return legacyModeService.isLegacyMode();
        }
        return false;
    }

    protected Category getExistingDefaultCategory(Product product) {
        //Make sure we get the actual field value - not something manipulated in the getter
        Category parentCategory;
        try {
            Field defaultCategory = ProductImpl.class.getDeclaredField("defaultCategory");
            defaultCategory.setAccessible(true);
            parentCategory = (Category) defaultCategory.get(product);
        } catch (NoSuchFieldException e) {
            throw ExceptionHelper.refineException(e);
        } catch (IllegalAccessException e) {
            throw ExceptionHelper.refineException(e);
        }
        return parentCategory;
    }

    protected void removeOldDefault(Product adminInstance, CategoryProductXref oldDefault, Entity entity) {
        if (!isDefaultCategoryLegacyMode()) {
            if (entity.findProperty("defaultCategory") != null && StringUtils.isEmpty(entity.findProperty("defaultCategory").getValue())) {
                adminInstance.setCategory(null);
            }
            CategoryProductXref newDefault = getCurrentDefaultXref(adminInstance);
            if (oldDefault != null && !oldDefault.equals(newDefault)) {
                adminInstance.getAllParentCategoryXrefs().remove(oldDefault);
            }
        }
    }

    protected void setupXref(Product adminInstance) {
        if (isDefaultCategoryLegacyMode()) {
            CategoryProductXref categoryXref = new CategoryProductXrefImpl();
            categoryXref.setCategory(getExistingDefaultCategory(adminInstance));
            categoryXref.setProduct(adminInstance);
            if (!adminInstance.getAllParentCategoryXrefs().contains(categoryXref) && categoryXref.getCategory() != null) {
                adminInstance.getAllParentCategoryXrefs().add(categoryXref);
            }
        }
    }

    protected CategoryProductXref getCurrentDefaultXref(Product product) {
        CategoryProductXref currentDefault = null;
        List<CategoryProductXref> xrefs = product.getAllParentCategoryXrefs();
        if (!CollectionUtils.isEmpty(xrefs)) {
            for (CategoryProductXref xref : xrefs) {
                if (xref.getCategory().isActive() && xref.getDefaultReference() != null && xref.getDefaultReference()) {
                    currentDefault = xref;
                    break;
                }
            }
        }
        return currentDefault;
    }
}
