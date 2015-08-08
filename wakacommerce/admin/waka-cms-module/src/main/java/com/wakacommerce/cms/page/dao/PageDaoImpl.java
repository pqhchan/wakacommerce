package com.wakacommerce.cms.page.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.cms.page.domain.PageField;
import com.wakacommerce.cms.page.domain.PageFieldImpl;
import com.wakacommerce.cms.page.domain.PageImpl;
import com.wakacommerce.cms.page.domain.PageTemplate;
import com.wakacommerce.cms.page.domain.PageTemplateImpl;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.common.util.dao.TQRestriction;
import com.wakacommerce.common.util.dao.TQRestriction.Mode;
import com.wakacommerce.common.util.dao.TypedQueryBuilder;

@Repository("blPageDao")
public class PageDaoImpl implements PageDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public Page readPageById(Long id) {
        return em.find(PageImpl.class, id);
    }

    @Override
    public List<PageField> readPageFieldsByPageId(Long pageId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<PageField> criteria = builder.createQuery(PageField.class);
        Root<PageFieldImpl> pageField = criteria.from(PageFieldImpl.class);
        criteria.select(pageField);

        criteria.where(builder.equal(pageField.get("page").get("id"), pageId));

        TypedQuery<PageField> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public PageTemplate readPageTemplateById(Long id) {
        return em.find(PageTemplateImpl.class, id);
    }
    

    @Override
    public PageTemplate savePageTemplate(PageTemplate template) {
        return em.merge(template);
    }

    @Override
    public Page updatePage(Page page) {
        return em.merge(page);
    }

    @Override
    public void delete(Page page) {
        if (!em.contains(page)) {
            page = readPageById(page.getId());
        }
        em.remove(page);
    }

    @Override
    public Page addPage(Page clonedPage) {
        return em.merge(clonedPage);
    }

    @Override
    public List<Page> findPageByURI(String uri) {
        TypedQuery<Page> q = new TypedQueryBuilder<Page>(Page.class, "p")
            .addRestriction("p.fullUrl", "=", uri)
            .addRestriction(new TQRestriction(Mode.OR)
                .addChildRestriction(new TQRestriction("p.activeStartDate", "IS NULL"))
                .addChildRestriction(new TQRestriction("p.activeStartDate", "<=", SystemTime.asDate())))
            .addRestriction(new TQRestriction(Mode.OR)
                .addChildRestriction(new TQRestriction("p.activeEndDate", "IS NULL"))
                .addChildRestriction(new TQRestriction("p.activeEndDate", ">=", SystemTime.asDate())))
            .addRestriction(new TQRestriction(Mode.OR)
                .addChildRestriction(new TQRestriction("p.offlineFlag", "IS NULL"))
                .addChildRestriction(new TQRestriction("p.offlineFlag", "=", false)))
            .toQuery(em);
        List<Page> pages = q.getResultList();
        return pages;
    }

    @Override
    public List<Page> readAllPages() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Page> criteria = builder.createQuery(Page.class);
        Root<PageImpl> page = criteria.from(PageImpl.class);

        criteria.select(page);

        try {
            return em.createQuery(criteria).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<Page>();
        }
    }
    
    @Override
    public List<Page> readOnlineAndIncludedPages(int limit, int offset, String sortBy) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Page> criteria = builder.createQuery(Page.class);
        Root<PageImpl> page = criteria.from(PageImpl.class);
        criteria.select(page);
        criteria.where(builder.and(
                builder.or(builder.isFalse(page.get("offlineFlag").as(Boolean.class)), builder.isNull(page.get("offlineFlag").as(Boolean.class))),
                builder.or(builder.isFalse(page.get("excludeFromSiteMap").as(Boolean.class)), builder.isNull(page.get("excludeFromSiteMap").as(Boolean.class)))));
        criteria.orderBy(builder.asc(page.get(sortBy)));
        TypedQuery<Page> query = em.createQuery(criteria);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<PageTemplate> readAllPageTemplates() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PageTemplate> criteria = builder.createQuery(PageTemplate.class);
        Root<PageTemplateImpl> template = criteria.from(PageTemplateImpl.class);

        criteria.select(template);

        try {
            return em.createQuery(criteria).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<PageTemplate>();
        }
    }

    @Override
    public void detachPage(Page page) {
        em.detach(page);
    }

}
