
package com.wakacommerce.core.order.dao;

import java.util.List;

import com.wakacommerce.core.order.domain.OrderMultishipOption;

/**
 * Provides support for reading OrderMultishipOptions.
 * The default Broadleaf implementation uses Hibernate to perform the reading.
 * 
 * 
 */
public interface OrderMultishipOptionDao {

    /**
     * Saves a given OrderMultishipOption. Note that the method will return the new
     * saved instance from Hibernate
     * 
     * @param orderMultishipOption the OrderMultishipOption to save
     * @return the saved instance from Hibernate
     */
    public OrderMultishipOption save(final OrderMultishipOption orderMultishipOption);

    /**
     * Returns all associated OrderMultishipOptions to the given order 
     * 
     * @param orderId the order's id to find OrderMultishipOptions for
     * @return the associated OrderMultishipOptions
     */
    public List<OrderMultishipOption> readOrderMultishipOptions(Long orderId);
    
    /**
     * Returns all associated OrderMultishipOptions to the given OrderItem
     * 
     * @param orderItemId the order item's id to find OrderMultishipOptions for
     * @return the associated OrderMultishipOptions
     */
    public List<OrderMultishipOption> readOrderItemOrderMultishipOptions(Long orderItemId);

    /**
     * Creates a new OrderMultishipOption instance.
     * 
     * The default Broadleaf implemntation uses the EntityConfiguration to create
     * the appropriate implementation class based on the current configuration
     * 
     * @return the OrderMultishipOption that was just created
     */
    public OrderMultishipOption create();

    /**
     * Removes all of the OrderMultishipOptions in the list permanently
     * 
     * @param options the options to delete
     */
    public void deleteAll(List<OrderMultishipOption> options);



}
