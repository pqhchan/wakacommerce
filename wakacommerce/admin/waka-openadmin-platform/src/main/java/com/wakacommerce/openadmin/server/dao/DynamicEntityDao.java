package com.wakacommerce.openadmin.server.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.type.Type;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.openadmin.dto.ClassTree;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.ForeignKey;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.dto.PersistencePerspective;
import com.wakacommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

public interface DynamicEntityDao {

    public abstract Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass);

    public abstract Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, boolean includeUnqualifiedPolymorphicEntities);

    public ClassTree getClassTreeFromCeiling(Class<?> ceilingClass);

    public ClassTree getClassTree(Class<?>[] polymorphicClasses);
    
    public abstract Map<String, FieldMetadata> getPropertiesForPrimitiveClass(String propertyName, String friendlyPropertyName, Class<?> targetClass, Class<?> parentClass, MergedPropertyType mergedPropertyType);
    
    public abstract Map<String, FieldMetadata> getMergedProperties(String ceilingEntityFullyQualifiedClassname, Class<?>[] entities, ForeignKey foreignField, String[] additionalNonPersistentProperties, ForeignKey[] additionalForeignFields, MergedPropertyType mergedPropertyType, Boolean populateManyToOneFields, String[] includeManyToOneFields, String[] excludeManyToOneFields, String configurationKey, String prefix);

    /**
     * Convenience method that obtains all of the {@link MergedPropertyType#PRIMARY} properties for a given class. Delegates to
     * {@link #getMergedProperties(String, Class[], ForeignKey, String[], ForeignKey[], MergedPropertyType, Boolean, String[], String[], String, String)}
     * @param cls
     * @return
     */
    public Map<String, FieldMetadata> getMergedProperties(@Nonnull Class<?> cls);
    
    public abstract <T> T persist(T entity);
    
    public abstract <T> T merge(T entity);

    public abstract Serializable retrieve(Class<?> entityClass, Object primaryKey);
    
    public abstract void remove(Serializable entity);
    
    public abstract void clear();
    
    public void flush();
    
    public void detach(Serializable entity);
    
    public void refresh(Serializable entity);

    public Object find(Class<?> entityClass, Object key);

    public EntityManager getStandardEntityManager();
    
    public void setStandardEntityManager(EntityManager entityManager);

    /**
     * Get the Hibernate PersistentClass instance associated with the fully-qualified
     * class name. Will return null if no persistent class is associated with this name.
     *
     * @param targetClassName
     * @return The PersistentClass instance
     */
    public PersistentClass getPersistentClass(String targetClassName);
    
    public Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective);

    public FieldManager getFieldManager();

    public EntityConfiguration getEntityConfiguration();

    public void setEntityConfiguration(EntityConfiguration entityConfiguration);

    public Map<String, Object> getIdMetadata(Class<?> entityClass);

    public List<Type> getPropertyTypes(Class<?> entityClass);

    public List<String> getPropertyNames(Class<?> entityClass);

    public Criteria createCriteria(Class<?> entityClass);

    public Field[] getAllFields(Class<?> targetClass);

    public Metadata getMetadata();

    public void setMetadata(Metadata metadata);
    
    public FieldMetadataProvider getDefaultFieldMetadataProvider();

    public SessionFactory getSessionFactory();

}
