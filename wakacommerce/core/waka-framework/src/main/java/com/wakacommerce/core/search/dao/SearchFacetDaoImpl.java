
package com.wakacommerce.core.search.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.catalog.domain.ProductImpl;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.FieldEntity;
import com.wakacommerce.core.search.domain.SearchFacet;
import com.wakacommerce.core.search.domain.SearchFacetImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

@Repository("blSearchFacetDao")
public class SearchFacetDaoImpl implements SearchFacetDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;
    
    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    
    @Override
    public List<SearchFacet> readAllSearchFacets(FieldEntity entityType) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<SearchFacet> criteria = builder.createQuery(SearchFacet.class);
        
        Root<SearchFacetImpl> facet = criteria.from(SearchFacetImpl.class);
        
        criteria.select(facet);
        criteria.where(
                builder.equal(facet.get("showOnSearch").as(Boolean.class), true),
                builder.equal(facet.join("field").get("entityType").as(String.class), entityType.getType())
        );

        TypedQuery<SearchFacet> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        
        return query.getResultList();
    }
    
    @Override
    public <T> List<T> readDistinctValuesForField(String fieldName, Class<T> fieldValueClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(fieldValueClass);
        
        Root<ProductImpl> product = criteria.from(ProductImpl.class);
        Path<Sku> sku = product.get("defaultSku");
        
        Path<?> pathToUse;
        if (fieldName.contains("defaultSku.")) {
            pathToUse = sku;
            fieldName = fieldName.substring("defaultSku.".length());
        } else if (fieldName.contains("productAttributes.")) {
            pathToUse = product.join("productAttributes");
            
            fieldName = fieldName.substring("productAttributes.".length());
            criteria.where(builder.equal(
                builder.lower(pathToUse.get("name").as(String.class)), fieldName.toLowerCase()));
            
            fieldName = "value";
        } else if (fieldName.contains("product.")) {
            pathToUse = product;
            fieldName = fieldName.substring("product.".length());
        } else {
            throw new IllegalArgumentException("Invalid facet fieldName specified: " + fieldName);
        }
        
        criteria.where(pathToUse.get(fieldName).as(fieldValueClass).isNotNull());
        criteria.distinct(true).select(pathToUse.get(fieldName).as(fieldValueClass));

        TypedQuery<T> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        
        return query.getResultList();
    }

    @Override
    public SearchFacet save(SearchFacet searchFacet) {
        return em.merge(searchFacet);
    }
}
