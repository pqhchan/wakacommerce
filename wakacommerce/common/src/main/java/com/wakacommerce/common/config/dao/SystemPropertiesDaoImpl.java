package com.wakacommerce.common.config.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.cache.AbstractCacheMissAware;
import com.wakacommerce.common.cache.PersistentRetrieval;
import com.wakacommerce.common.config.domain.SystemProperty;
import com.wakacommerce.common.config.domain.SystemPropertyImpl;
import com.wakacommerce.common.extensibility.jpa.SiteDiscriminator;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.web.WakaRequestContext;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository("blSystemPropertiesDao")
public class SystemPropertiesDaoImpl extends AbstractCacheMissAware implements SystemPropertiesDao{

    protected static final Log LOG = LogFactory.getLog(SystemPropertiesDaoImpl.class);

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    
    @Override
    public SystemProperty readById(Long id) {
        return em.find(SystemPropertyImpl.class, id);
    }

    @Override
    public SystemProperty saveSystemProperty(SystemProperty systemProperty) {
        return em.merge(systemProperty);
    }

    @Override
    public void deleteSystemProperty(SystemProperty systemProperty) {
        em.remove(systemProperty);
    }

    @Override
    public List<SystemProperty> readAllSystemProperties() {
        TypedQuery<SystemProperty> query = em.createNamedQuery("BC_READ_ALL_SYSTEM_PROPERTIES", SystemProperty.class);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public SystemProperty readSystemPropertyByName(final String name) {
        return getCachedObject(SystemProperty.class, "blSystemPropertyNullCheckCache", "SYSTEM_PROPERTY_MISSING_CACHE_HIT_RATE", new PersistentRetrieval<SystemProperty>() {
            @Override
            public SystemProperty retrievePersistentObject() {
                TypedQuery<SystemProperty> query = em.createNamedQuery("BC_READ_SYSTEM_PROPERTIES_BY_NAME", SystemProperty.class);
                query.setParameter("propertyName", name);
                query.setHint(QueryHints.HINT_CACHEABLE, true);
                List<SystemProperty> props = query.getResultList();
                if (props != null && ! props.isEmpty()) {
                    return props.get(0);
                }
                return null;
            }
        }, name, getSite());
    }

    @Override
    public void removeFromCache(SystemProperty systemProperty) {
        String site = "";
        if (systemProperty instanceof SiteDiscriminator && ((SiteDiscriminator) systemProperty).getSiteDiscriminator() != null) {
            site = String.valueOf(((SiteDiscriminator) systemProperty).getSiteDiscriminator());
        }
        super.removeItemFromCache("blSystemPropertyNullCheckCache", systemProperty.getName(), site);
    }

    @Override
    public SystemProperty createNewSystemProperty() {
        return (SystemProperty)entityConfiguration.createEntityInstance(SystemProperty.class.getName());
    }

    @Override
    protected Log getLogger() {
        return LOG;
    }

    protected String getSite() {
        String site = "";
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            if (brc.getNonPersistentSite() != null) {
                site = String.valueOf(brc.getNonPersistentSite().getId());
            }
        }
        return site;
    }
}
