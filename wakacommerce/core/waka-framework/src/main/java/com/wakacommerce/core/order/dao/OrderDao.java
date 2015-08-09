
package com.wakacommerce.core.order.dao;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderLock;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;

public interface OrderDao {

    Order readOrderById(Long orderId);
    
    List<Order> readOrdersByIds(List<Long> orderIds);

    List<Order> readBatchOrders(int start, int pageSize, List<OrderStatus> statuses);

    Order readOrderById(Long orderId, boolean refresh);

    List<Order> readOrdersForCustomer(Customer customer, OrderStatus orderStatus);

    List<Order> readOrdersForCustomer(Long id);

    Order readNamedOrderForCustomer(Customer customer, String name);

    Order readCartForCustomer(Customer customer);

    Order save(Order order);

    void delete(Order order);

    Order submitOrder(Order cartOrder);

    Order create();

    Order createNewCartForCustomer(Customer customer);

    Order readOrderByOrderNumber(String orderNumber);
    
    Order updatePrices(Order order);

    public boolean acquireLock(Order order);

    public boolean releaseLock(Order order);

}
