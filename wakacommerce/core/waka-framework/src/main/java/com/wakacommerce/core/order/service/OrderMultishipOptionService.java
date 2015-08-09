
package com.wakacommerce.core.order.service;

import java.util.List;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderMultishipOption;
import com.wakacommerce.core.order.service.call.OrderMultishipOptionDTO;

/**
 *
 * @ hui
 */
public interface OrderMultishipOptionService {

    public OrderMultishipOption save(OrderMultishipOption orderMultishipOption);

    public List<OrderMultishipOption> findOrderMultishipOptions(Long orderId);

    public List<OrderMultishipOption> findOrderItemOrderMultishipOptions(Long orderItemId);

    public OrderMultishipOption create();

    public void deleteAllOrderMultishipOptions(Order order);

    public void deleteOrderItemOrderMultishipOptions(Long orderItemId);

    public void deleteOrderItemOrderMultishipOptions(Long orderItemId, int numToDelete);

    public List<OrderMultishipOption> generateOrderMultishipOptions(Order order);

    public List<OrderMultishipOption> getOrGenerateOrderMultishipOptions(Order order);

    public List<OrderMultishipOption> getOrderMultishipOptionsFromDTOs(Order order, List<OrderMultishipOptionDTO> optionDtos);

    public void saveOrderMultishipOptions(Order order, List<OrderMultishipOptionDTO> optionDTOs);



}
