
package com.wakacommerce.core.order.fulfillment.domain;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentOption;

/**
 *
 * @ hui
 */
public interface BandedWeightFulfillmentOption extends FulfillmentOption {

    public List<FulfillmentWeightBand> getBands();

    public void setBands(List<FulfillmentWeightBand> bands);

}
