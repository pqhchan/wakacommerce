
package com.wakacommerce.core.payment.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.OrderPaymentImpl;
import com.wakacommerce.core.payment.domain.PaymentLog;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.payment.domain.PaymentTransactionImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("blOrderPaymentDao")
public class OrderPaymentDaoImpl implements OrderPaymentDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public OrderPayment save(OrderPayment payment) {
        return em.merge(payment);
    }

    @Override
    public PaymentTransaction save(PaymentTransaction transaction) {
        return em.merge(transaction);
    }

    @Override
    public PaymentLog save(PaymentLog log) {
        return em.merge(log);
    }

    @Override
    public OrderPayment readPaymentById(Long paymentId) {
        return em.find(OrderPaymentImpl.class, paymentId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderPayment> readPaymentsForOrder(Order order) {
        Query query = em.createNamedQuery("BC_READ_ORDERS_PAYMENTS_BY_ORDER_ID");
        query.setParameter("orderId", order.getId());
        return query.getResultList();
    }

    @Override
    public OrderPayment create() {
        return ((OrderPayment) entityConfiguration.createEntityInstance(OrderPayment.class.getName()));
    }

    @Override
    public PaymentTransaction createTransaction() {
        return entityConfiguration.createEntityInstance(PaymentTransaction.class.getName(), PaymentTransaction.class);
    }

    @Override
    public PaymentTransaction readTransactionById(Long transactionId) {
        return em.find(PaymentTransactionImpl.class, transactionId);
    }

    @Override
    public PaymentLog createLog() {
        return entityConfiguration.createEntityInstance(PaymentLog.class.getName(), PaymentLog.class);
    }

    @Override
    public void delete(OrderPayment paymentInfo) {
        if (!em.contains(paymentInfo)) {
            paymentInfo = readPaymentById(paymentInfo.getId());
        }
        em.remove(paymentInfo);
    }
}
