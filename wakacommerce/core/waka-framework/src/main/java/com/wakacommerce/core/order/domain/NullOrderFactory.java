
package com.wakacommerce.core.order.domain;

/**
 * <p>Generates a shared, static instance of NullOrderImpl.</p>
 * 
 * @see com.wakacommerce.core.order.domain.NullOrderImpl
 *  
 */
public interface NullOrderFactory {
    
    public Order getNullOrder();
    
}
