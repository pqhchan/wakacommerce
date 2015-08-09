
package com.wakacommerce.core.payment.service;

import java.util.List;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.PaymentLog;
import com.wakacommerce.core.payment.domain.PaymentTransaction;

public interface OrderPaymentService {

    public OrderPayment save(OrderPayment payment);

    public PaymentTransaction save(PaymentTransaction transaction);

    public PaymentLog save(PaymentLog log);

    public OrderPayment readPaymentById(Long paymentId);

    public List<OrderPayment> readPaymentsForOrder(Order order);

    public OrderPayment create();

    public void delete(OrderPayment payment);

    public PaymentTransaction createTransaction();

    public PaymentTransaction readTransactionById(Long transactionId);

    public PaymentLog createLog();

}
