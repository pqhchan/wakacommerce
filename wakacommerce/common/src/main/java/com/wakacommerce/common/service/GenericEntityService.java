  

package com.wakacommerce.common.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 *
 * @ hui
 */
public interface GenericEntityService {

    public Object readGenericEntity(String className, Object id);

    <T> T readGenericEntity(Class<T> clazz, Object id);

    public <T> T save(T object);

    void persist(Object object);

    public <T> Long readCountGenericEntity(Class<T> clazz);

    public <T> List<T> readAllGenericEntity(Class<T> clazz, int limit, int offset);

    List<Long> readAllGenericEntityId(Class<?> clazz);

    Serializable getIdentifier(Object entity);

    void flush();

    void clearAutoFlushMode();

    void enableAutoFlushMode();

    void clear();

    boolean sessionContains(Object object);

    boolean idAssigned(Object object);

    Class<?> getCeilingImplClass(String className);

    EntityManager getEntityManager();

    void remove(Object object);
}
