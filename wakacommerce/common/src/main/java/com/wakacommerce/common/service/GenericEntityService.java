  

package com.wakacommerce.common.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * CRUD methods for generic entities
 * 
 * 
 */
public interface GenericEntityService {

    /**
     * Finds a generic entity by a classname and id
     * 
     * @param className
     * @param id
     * @return the entity
     */
    public Object readGenericEntity(String className, Object id);

    <T> T readGenericEntity(Class<T> clazz, Object id);

    /**
     * Saves a generic entity
     * 
     * @param object
     * @return the persisted version of the entity
     */
    public <T> T save(T object);

    /**
     * Persist the new object
     *
     * @param object
     */
    void persist(Object object);

    /**
     * Finds how many of the given entity class are persisted
     * 
     * @param clazz
     * @return the count of the generic entity
     */
    public <T> Long readCountGenericEntity(Class<T> clazz);

    /**
     * Finds all generic entities for a given classname, with pagination options.
     * 
     * @param clazz
     * @param limit
     * @param offset
     * @return the entities
     */
    public <T> List<T> readAllGenericEntity(Class<T> clazz, int limit, int offset);

    List<Long> readAllGenericEntityId(Class<?> clazz);

    /**
     * Retrieve the identifier from the Hibernate entity (the entity must reside in the current session)
     *
     * @param entity
     * @return
     */
    Serializable getIdentifier(Object entity);

    /**
     * Flush changes to the persistence store
     */
    void flush();

    void clearAutoFlushMode();

    void enableAutoFlushMode();

    /**
     * Clear level 1 cache
     */
    void clear();

    /**
     * Whether or not the current hibernate session (level 1) contains the object
     *
     * @param object
     * @return
     */
    boolean sessionContains(Object object);

    /**
     * Whether or not this object is an {@link javax.persistence.Entity} and whether or not it already has an id assigned
     * @param object
     * @return
     */
    boolean idAssigned(Object object);

    /**
     * Return the ceiling implementation class for an entity
     *
     * @param className
     * @return
     */
    Class<?> getCeilingImplClass(String className);

    /**
     * Return the entity manager
     *
     * @return
     */
    EntityManager getEntityManager();

    /**
     * Attempt to delete the entity
     *
     * @param object
     */
    void remove(Object object);
}
