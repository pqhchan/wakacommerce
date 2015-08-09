
package com.wakacommerce.core.util.dao;

import java.util.Date;
import java.util.List;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.profile.core.domain.Customer;

/**
 *
 * @ hui
 */
public interface ResourcePurgeDao {

    List<Order> findCarts(String[] names, OrderStatus[] statuses, Date dateCreatedMinThreshold, Boolean isPreview);

    List<Order> findCarts(String[] names, OrderStatus[] statuses, Date dateCreatedMinThreshold, Boolean isPreview, int startPos, int length);

    Long findCartsCount(String[] names, OrderStatus[] statuses, Date dateCreatedMinThreshold, Boolean isPreview);

    List<Customer> findCustomers(Date dateCreatedMinThreshold, Boolean registered, Boolean deactivated, Boolean isPreview);

    List<Customer> findCustomers(Date dateCreatedMinThreshold, Boolean registered, Boolean deactivated, Boolean isPreview, int startPos, int length);

    Long findCustomersCount(Date dateCreatedMinThreshold, Boolean registered, Boolean deactivated, Boolean isPreview);
}
