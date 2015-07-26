
package com.wakacommerce.openadmin.server.service.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.handler.CustomPersistenceHandler;

public interface PersistenceManager {

    public abstract Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass);

    public abstract Class<?>[] getPolymorphicEntities(String ceilingEntityFullyQualifiedClassname) throws ClassNotFoundException;

    public abstract Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective) throws ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;

    public abstract ClassMetadata getMergedClassMetadata(Class<?>[] entities, Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties) throws ClassNotFoundException, IllegalArgumentException;

    public abstract PersistenceResponse inspect(PersistencePackage persistencePackage) throws ServiceException, ClassNotFoundException;

    public abstract PersistenceResponse fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

    public abstract PersistenceResponse add(PersistencePackage persistencePackage) throws ServiceException;

    public abstract PersistenceResponse update(PersistencePackage persistencePackage) throws ServiceException;

    public abstract PersistenceResponse remove(PersistencePackage persistencePackage) throws ServiceException;

    public abstract DynamicEntityDao getDynamicEntityDao();

    public abstract void setDynamicEntityDao(DynamicEntityDao dynamicEntityDao);

    public abstract Map<String, String> getTargetEntityManagers();

    public abstract void setTargetEntityManagers(Map<String, String> targetEntityManagers);

    public abstract TargetModeType getTargetMode();

    public abstract void setTargetMode(TargetModeType targetMode);

    public abstract List<CustomPersistenceHandler> getCustomPersistenceHandlers();

    public abstract void setCustomPersistenceHandlers(List<CustomPersistenceHandler> customPersistenceHandlers);

    public abstract Class<?>[] getUpDownInheritance(Class<?> testClass);

    public abstract Class<?>[] getUpDownInheritance(String testClassname) throws ClassNotFoundException;

}
