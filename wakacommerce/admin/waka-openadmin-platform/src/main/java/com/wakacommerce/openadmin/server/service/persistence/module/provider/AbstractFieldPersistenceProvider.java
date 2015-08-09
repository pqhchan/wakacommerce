
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import org.apache.commons.lang.ArrayUtils;

import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.dao.FieldInfo;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceManager;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @ hui
 */
public abstract class AbstractFieldPersistenceProvider implements FieldPersistenceProvider {

    protected Class<?> getListFieldType(Serializable instance, FieldManager fieldManager, Property property, PersistenceManager persistenceManager) {
        Class<?> returnType = null;
        Field field = fieldManager.getField(instance.getClass(), property.getName());
        java.lang.reflect.Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Class<?> clazz = (Class<?>) pType.getActualTypeArguments()[0];
            Class<?>[] entities = persistenceManager.getDynamicEntityDao().getAllPolymorphicEntitiesFromCeiling(clazz);
            if (!ArrayUtils.isEmpty(entities)) {
                returnType = entities[entities.length-1];
            }
        }
        return returnType;
    }

    protected Class<?> getMapFieldType(Serializable instance, FieldManager fieldManager, Property property, PersistenceManager persistenceManager) {
        Class<?> returnType = null;
        Field field = fieldManager.getField(instance.getClass(), property.getName().substring(0, property.getName().indexOf(FieldManager.MAPFIELDSEPARATOR)));
        java.lang.reflect.Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Class<?> clazz = (Class<?>) pType.getActualTypeArguments()[1];
            Class<?>[] entities = persistenceManager.getDynamicEntityDao().getAllPolymorphicEntitiesFromCeiling(clazz);
            if (!ArrayUtils.isEmpty(entities)) {
                returnType = entities[entities.length-1];
            }
        }
        return returnType;
    }

    protected FieldInfo buildFieldInfo(Field field) {
        FieldInfo info = new FieldInfo();
        info.setName(field.getName());
        info.setGenericType(field.getGenericType());
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
        if (manyToMany != null) {
            info.setManyToManyMappedBy(manyToMany.mappedBy());
            info.setManyToManyTargetEntity(manyToMany.targetEntity().getName());
        }
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        if (oneToMany != null) {
            info.setOneToManyMappedBy(oneToMany.mappedBy());
            info.setOneToManyTargetEntity(oneToMany.targetEntity().getName());
        }
        return info;
    }

    @Override
    public boolean alwaysRun() {
        return false;
    }
}
