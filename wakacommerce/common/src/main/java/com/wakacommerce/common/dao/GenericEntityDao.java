package com.wakacommerce.common.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.wakacommerce.common.persistence.EntityConfiguration;

public interface GenericEntityDao {

    public <T> T readGenericEntity(Class<T> clazz, Object id);

    public Class<?> getImplClass(String className);

    Class<?> getCeilingImplClass(String className);

    public <T> T save(T object);

    void persist(Object object);

    void remove(Object object);

    public <T> Long readCountGenericEntity(Class<T> clazz);

    public <T> List<T> readAllGenericEntity(Class<T> clazz, int limit, int offset);

    <T> List<T> readAllGenericEntity(Class<T> clazz);

    List<Long> readAllGenericEntityId(Class<?> clazz);

    Serializable getIdentifier(Object entity);

    void flush();

    void clearAutoFlushMode();

    void enableAutoFlushMode();

    void clear();

    boolean sessionContains(Object object);

    boolean idAssigned(Object object);

    EntityManager getEntityManager();
}
