
package com.wakacommerce.core.order.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.FulfillmentOptionImpl;
import com.wakacommerce.core.order.service.type.FulfillmentType;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @ hui
 */
@Repository("blFulfillmentOptionDao")
public class FulfillmentOptionDaoImpl implements FulfillmentOptionDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public FulfillmentOption readFulfillmentOptionById(final Long fulfillmentOptionId) {
        return em.find(FulfillmentOptionImpl.class, fulfillmentOptionId);
    }

    @Override
    public FulfillmentOption save(FulfillmentOption option) {
        return em.merge(option);
    }

    @Override
    public List<FulfillmentOption> readAllFulfillmentOptions() {
        TypedQuery<FulfillmentOption> query = em.createNamedQuery("BC_READ_ALL_FULFILLMENT_OPTIONS", FulfillmentOption.class);
        return query.getResultList();
    }

    @Override
    public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type) {
        TypedQuery<FulfillmentOption> query = em.createNamedQuery("BC_READ_ALL_FULFILLMENT_OPTIONS_BY_TYPE", FulfillmentOption.class);
        query.setParameter("fulfillmentType", type.getType());
        return query.getResultList();
    }
}
