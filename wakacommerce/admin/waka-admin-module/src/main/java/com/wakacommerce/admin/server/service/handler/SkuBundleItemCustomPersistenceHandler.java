/*
 * #%L
 * BroadleafCommerce Admin Module
 * %%
 * Copyright (C) 2009 - 2014 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.admin.server.service.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;
import com.wakacommerce.core.catalog.domain.SkuBundleItemImpl;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.InspectHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Overridden to provide the option values field on the SkuBundleItem list
 * 
 *Phillip Verheyden (phillipuniverse)
 */
@Component("blSkuBundleItemCustomPersistenceHandler")
public class SkuBundleItemCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(SkuBundleItemCustomPersistenceHandler.class);
    
    @Resource(name = "blSkuCustomPersistenceHandler")
    protected SkuCustomPersistenceHandler skuPersistenceHandler;
    
    @Override
    public Boolean canHandleInspect(PersistencePackage persistencePackage) {
        return canHandle(persistencePackage);
    }
    
    @Override
    public Boolean canHandleFetch(PersistencePackage persistencePackage) {
        return canHandle(persistencePackage);
    }
    
    protected Boolean canHandle(PersistencePackage persistencePackage) {
        String className = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        try {
            return SkuBundleItem.class.isAssignableFrom(Class.forName(className));
        } catch (ClassNotFoundException e) {
            LOG.warn("Could not find the class " + className + ", skipping the inventory custom persistence handler");
            return false;
        }
    }
    
    @Override
    public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, InspectHelper helper) throws ServiceException {
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Map<MergedPropertyType, Map<String, FieldMetadata>> allMergedProperties = new HashMap<MergedPropertyType, Map<String, FieldMetadata>>();

            //retrieve the default properties for Inventory
            Map<String, FieldMetadata> properties = helper.getSimpleMergedProperties(SkuBundleItem.class.getName(), persistencePerspective);

            //add in the consolidated product options field
            FieldMetadata options = skuPersistenceHandler.createConsolidatedOptionField(SkuBundleItemImpl.class);
            options.setOrder(3);
            properties.put(SkuCustomPersistenceHandler.CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME, options);
            
            allMergedProperties.put(MergedPropertyType.PRIMARY, properties);
            Class<?>[] entityClasses = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(SkuBundleItem.class);
            ClassMetadata mergedMetadata = helper.getMergedClassMetadata(entityClasses, allMergedProperties);

            return new DynamicResultSet(mergedMetadata, null, null);

        } catch (Exception e) {
            String className = persistencePackage.getCeilingEntityFullyQualifiedClassname();
            ServiceException ex = new ServiceException("Unable to retrieve inspection results for " + className, e);
            LOG.error("Unable to retrieve inspection results for " + className, ex);
            throw ex;
        }
    }

    @Override
    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            //get the default properties from Inventory and its subclasses
            Map<String, FieldMetadata> originalProps = helper.getSimpleMergedProperties(SkuBundleItem.class.getName(), persistencePerspective);

            //Pull back the Inventory based on the criteria from the client
            List<FilterMapping> filterMappings = helper.getFilterMappings(persistencePerspective, cto, ceilingEntityFullyQualifiedClassname, originalProps);

            //attach the product option criteria
            skuPersistenceHandler.applyProductOptionValueCriteria(filterMappings, cto, persistencePackage, "sku");
            
            List<Serializable> records = helper.getPersistentRecords(persistencePackage.getCeilingEntityFullyQualifiedClassname(), filterMappings, cto.getFirstResult(), cto.getMaxResults());
            //Convert Skus into the client-side Entity representation
            Entity[] payload = helper.getRecords(originalProps, records);

            int totalRecords = helper.getTotalRecords(persistencePackage.getCeilingEntityFullyQualifiedClassname(), filterMappings);
            
            for (int i = 0; i < payload.length; i++) {
                Entity entity = payload[i];
                SkuBundleItem bundleItem = (SkuBundleItem) records.get(i);

                Property optionsProperty = skuPersistenceHandler.getConsolidatedOptionProperty(bundleItem.getSku().getProductOptionValuesCollection());
                entity.addProperty(optionsProperty);
            }

            return new DynamicResultSet(null, payload, totalRecords);
        } catch (Exception e) {
            throw new ServiceException("There was a problem fetching inventory. See server logs for more details", e);
        }
    }
    
}
