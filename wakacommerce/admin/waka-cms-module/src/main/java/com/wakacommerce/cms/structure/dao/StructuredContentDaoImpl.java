package com.wakacommerce.cms.structure.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.cms.structure.domain.StructuredContentImpl;
import com.wakacommerce.cms.structure.domain.StructuredContentType;
import com.wakacommerce.cms.structure.domain.StructuredContentTypeImpl;
import com.wakacommerce.common.persistence.EntityConfiguration;

@Repository("blStructuredContentDao")
public class StructuredContentDaoImpl implements StructuredContentDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public StructuredContent findStructuredContentById(Long contentId) {
        return em.find(StructuredContentImpl.class, contentId);
    }

    @Override
    public StructuredContentType findStructuredContentTypeById(Long contentTypeId) {
        return em.find(StructuredContentTypeImpl.class, contentTypeId);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StructuredContentType> retrieveAllStructuredContentTypes() {
        Query query = em.createNamedQuery("BC_READ_ALL_STRUCTURED_CONTENT_TYPES");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }
    
    @Override
    public List<StructuredContent> findAllContentItems() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StructuredContent> criteria = builder.createQuery(StructuredContent.class);
        Root<StructuredContentImpl> sc = criteria.from(StructuredContentImpl.class);
        criteria.select(sc);
        TypedQuery<StructuredContent> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public StructuredContent addOrUpdateContentItem(StructuredContent content) {
    	// 实现的貌似有问题！！ 后面有时间记得改下
    	return em.merge(content);
    }

    @Override
    public void delete(StructuredContent content) {
        if (! em.contains(content)) {
            content = findStructuredContentById(content.getId());
        }
        em.remove(content);
    }
    
    @Override
    public StructuredContentType saveStructuredContentType(StructuredContentType type) {
        return em.merge(type);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StructuredContent> findActiveStructuredContentByType(StructuredContentType type) {
        Query query = em.createNamedQuery("BC_ACTIVE_STRUCTURED_CONTENT_BY_TYPE");
        query.setParameter("contentType", type);
        query.setHint(QueryHints.HINT_CACHEABLE, true);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StructuredContent> findActiveStructuredContentByNameAndType(StructuredContentType type, String name) {
        final Query query = em.createNamedQuery("BC_ACTIVE_STRUCTURED_CONTENT_BY_TYPE_AND_NAME");
        query.setParameter("contentType", type);
        query.setParameter("contentName", name);
        query.setHint(QueryHints.HINT_CACHEABLE, true);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<StructuredContent> findActiveStructuredContentByName(String name) {
        Query query = em.createNamedQuery("BC_ACTIVE_STRUCTURED_CONTENT_BY_NAME");
        query.setParameter("contentName", name);
        query.setHint(QueryHints.HINT_CACHEABLE, true);

        return query.getResultList();
    }

    @Override
    public StructuredContentType findStructuredContentTypeByName(String name) {
        Query query = em.createNamedQuery("BC_READ_STRUCTURED_CONTENT_TYPE_BY_NAME");
        query.setParameter("name",name);
        query.setHint(QueryHints.HINT_CACHEABLE, true);

        @SuppressWarnings("unchecked")
		List<StructuredContentType> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void detach(StructuredContent sc) {
        em.detach(sc);
    }
}
