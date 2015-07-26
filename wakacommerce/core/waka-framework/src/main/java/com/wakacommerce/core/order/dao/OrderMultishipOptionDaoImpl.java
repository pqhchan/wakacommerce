
package com.wakacommerce.core.order.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.order.domain.OrderMultishipOption;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository("blOrderMultishipOptionDao")
public class OrderMultishipOptionDaoImpl implements OrderMultishipOptionDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    @Transactional("blTransactionManager")
    public OrderMultishipOption save(final OrderMultishipOption orderMultishipOption) {
        return em.merge(orderMultishipOption);
    }

    @Override
    public List<OrderMultishipOption> readOrderMultishipOptions(final Long orderId) {
        TypedQuery<OrderMultishipOption> query = em.createNamedQuery("BC_READ_MULTISHIP_OPTIONS_BY_ORDER_ID", OrderMultishipOption.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    
    @Override
    public List<OrderMultishipOption> readOrderItemOrderMultishipOptions(final Long orderItemId) {
        TypedQuery<OrderMultishipOption> query = em.createNamedQuery("BC_READ_MULTISHIP_OPTIONS_BY_ORDER_ITEM_ID", OrderMultishipOption.class);
        query.setParameter("orderItemId", orderItemId);
        return query.getResultList();
    }
    
    @Override
    public OrderMultishipOption create() {
        return (OrderMultishipOption) entityConfiguration.createEntityInstance(OrderMultishipOption.class.getName());
    }
    
    @Override
    @Transactional("blTransactionManager")
    public void deleteAll(List<OrderMultishipOption> options) {
        for (OrderMultishipOption option : options) {
            em.remove(option);
        }
    }
}
