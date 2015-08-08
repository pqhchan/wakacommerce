package com.wakacommerce.common.util.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface DynamicDaoHelper {

    public Map<String, Object> getIdMetadata(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public List<String> getPropertyNames(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public List<Type> getPropertyTypes(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public SessionFactory getSessionFactory(HibernateEntityManager entityManager);

    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, SessionFactory sessionFactory, boolean includeUnqualifiedPolymorphicEntities, boolean useCache);

    public Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities);

    public boolean isExcludeClassFromPolymorphism(Class<?> clazz);

    Serializable getIdentifier(Object entity, EntityManager em);

    Serializable getIdentifier(Object entity, Session session);

    Field getIdField(Class<?> clazz, EntityManager em);

    Field getIdField(Class<?> clazz, Session session);

}
