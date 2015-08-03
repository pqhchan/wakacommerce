package com.wakacommerce.cms.file.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.cms.file.domain.StaticAsset;
import com.wakacommerce.cms.file.domain.StaticAssetImpl;
import com.wakacommerce.common.persistence.EntityConfiguration;

@Repository("blStaticAssetDao")
public class StaticAssetDaoImpl implements StaticAssetDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Resource(name = "blStaticAssetDaoQueryExtensionManager")
    protected StaticAssetDaoQueryExtensionManager queryExtensionManager;

    @Override
    public StaticAsset readStaticAssetById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StaticAsset> criteria = builder.createQuery(StaticAsset.class);
        Root<StaticAssetImpl> handler = criteria.from(StaticAssetImpl.class);
        criteria.select(handler);
        List<Predicate> restrictions = new ArrayList<Predicate>();
        restrictions.add(builder.equal(handler.get("id"), id));

        if (queryExtensionManager != null) {
            queryExtensionManager.getProxy().setup(StaticAssetImpl.class, null);
            queryExtensionManager.getProxy().refineRetrieve(StaticAssetImpl.class, null, builder, criteria, handler, restrictions);
        }
        criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));

        TypedQuery<StaticAsset> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        List<StaticAsset> response = query.getResultList();
        if (response.size() > 0) {
            return response.get(0);
        }
        return null;
    }
    
    public List<StaticAsset> readAllStaticAssets() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StaticAsset> criteria = builder.createQuery(StaticAsset.class);
        Root<StaticAssetImpl> handler = criteria.from(StaticAssetImpl.class);
        criteria.select(handler);
        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();
        try {
            if (queryExtensionManager != null) {
                queryExtensionManager.getProxy().setup(StaticAssetImpl.class, null);
                queryExtensionManager.getProxy().refineRetrieve(StaticAssetImpl.class, null, builder, criteria, handler, restrictions);
                queryExtensionManager.getProxy().refineOrder(StaticAssetImpl.class, null, builder, criteria, handler, sorts);
            }
            criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));
            return em.createQuery(criteria).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<StaticAsset>();
        }
    }

    @Override
    public StaticAsset readStaticAssetByFullUrl(String fullUrl) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StaticAsset> criteria = builder.createQuery(StaticAsset.class);
        Root<StaticAssetImpl> handler = criteria.from(StaticAssetImpl.class);
        criteria.select(handler);

        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();
        restrictions.add(builder.equal(handler.get("fullUrl"), fullUrl));
        try {
            if (queryExtensionManager != null) {
                queryExtensionManager.getProxy().setup(StaticAssetImpl.class, null);
                queryExtensionManager.getProxy().refineRetrieve(StaticAssetImpl.class, null, builder, criteria, handler, restrictions);
                queryExtensionManager.getProxy().refineOrder(StaticAssetImpl.class, null, builder, criteria, handler, sorts);
            }
            criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(sorts)) {
                criteria.orderBy(sorts);
            }

            TypedQuery<StaticAsset> query = em.createQuery(criteria);
            query.setHint(QueryHints.HINT_CACHEABLE, true);
            List<StaticAsset> response = query.getResultList();
            if (response.size() > 0) {
                return response.get(0);
            }
            return null;
        } finally {
            if (queryExtensionManager != null) {
                queryExtensionManager.getProxy().breakdown(StaticAssetImpl.class, null);
            }
        }
    }

    @Override
    public StaticAsset addOrUpdateStaticAsset(StaticAsset asset, boolean clearLevel1Cache) {
        if (clearLevel1Cache) {
            em.detach(asset);
        }
        return em.merge(asset);
    }

    @Override
    public void delete(StaticAsset asset) {
        if (!em.contains(asset)) {
            asset = readStaticAssetById(asset.getId());
        }
        em.remove(asset);
    }

}
