
package com.wakacommerce.core.order.dao;

import java.util.List;

import com.wakacommerce.core.order.domain.OrderMultishipOption;

/**
 *
 * @ hui
 */
public interface OrderMultishipOptionDao {

    public OrderMultishipOption save(final OrderMultishipOption orderMultishipOption);

    public List<OrderMultishipOption> readOrderMultishipOptions(Long orderId);

    public List<OrderMultishipOption> readOrderItemOrderMultishipOptions(Long orderItemId);

    public OrderMultishipOption create();

    public void deleteAll(List<OrderMultishipOption> options);



}
