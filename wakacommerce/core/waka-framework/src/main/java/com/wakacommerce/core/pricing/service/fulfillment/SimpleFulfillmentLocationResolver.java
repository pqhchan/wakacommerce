
package com.wakacommerce.core.pricing.service.fulfillment;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.profile.core.domain.Address;

/**
 *
 * @ hui
 */
public class SimpleFulfillmentLocationResolver implements FulfillmentLocationResolver {

    protected Address address;

    @Override
    public Address resolveLocationForFulfillmentGroup(FulfillmentGroup group) {
        return address;
    }

    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

}
