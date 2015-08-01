
package com.wakacommerce.core.order.fulfillment.domain;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentOption;

/**
 * 
 *  
 */
public interface BandedPriceFulfillmentOption extends FulfillmentOption {
    
    public List<FulfillmentPriceBand> getBands();

    public void setBands(List<FulfillmentPriceBand> bands);

}
