  

package com.wakacommerce.common.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.dao.GenericEntityDao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;


@Service("blGenericEntityService")
public class GenericEntityServiceImpl implements GenericEntityService {
    
    @Resource(name = "blGenericEntityDao")
    protected GenericEntityDao genericEntityDao;
    
    @Override
    public Object readGenericEntity(String className, Object id) {
        Class<?> clazz = genericEntityDao.getImplClass(className);
        return genericEntityDao.readGenericEntity(clazz, id);
    }

    @Override
    public <T> T readGenericEntity(Class<T> clazz, Object id) {
        return genericEntityDao.readGenericEntity(clazz, id);
    }
    
    @Override
    public <T> T save(T object) {
        return genericEntityDao.save(object);
    }

    public void persist(Object object) {
        genericEntityDao.persist(object);
    }

    @Override
    public <T> Long readCountGenericEntity(Class<T> clazz) {
        return genericEntityDao.readCountGenericEntity(clazz);
    }

    @Override
    public <T> List<T> readAllGenericEntity(Class<T> clazz, int limit, int offset) {
        return genericEntityDao.readAllGenericEntity(clazz, limit, offset);
    }

    @Override
    public List<Long> readAllGenericEntityId(Class<?> clazz) {
        return genericEntityDao.readAllGenericEntityId(clazz);
    }

    @Override
    public Serializable getIdentifier(Object entity) {
        return genericEntityDao.getIdentifier(entity);
    }

    @Override
    public void flush() {
        genericEntityDao.flush();
    }

    @Override
    public void clearAutoFlushMode() {
        genericEntityDao.clearAutoFlushMode();
    }

    @Override
    public void enableAutoFlushMode() {
        genericEntityDao.enableAutoFlushMode();
    }

    @Override
    public void clear() {
        genericEntityDao.clear();
    }

    @Override
    public boolean sessionContains(Object object) {
        return genericEntityDao.sessionContains(object);
    }

    @Override
    public Class<?> getCeilingImplClass(String className) {
        return genericEntityDao.getCeilingImplClass(className);
    }

    @Override
    public boolean idAssigned(Object object) {
        return genericEntityDao.idAssigned(object);
    }

    @Override
    public EntityManager getEntityManager() {
        return genericEntityDao.getEntityManager();
    }

    @Override
    public void remove(Object object) {
        genericEntityDao.remove(object);
    }
}
