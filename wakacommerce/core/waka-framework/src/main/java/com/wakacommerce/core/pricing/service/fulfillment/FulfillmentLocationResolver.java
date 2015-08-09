
package com.wakacommerce.core.pricing.service.fulfillment;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.profile.core.domain.Address;

/**
 *
 * @ hui
 */
public interface FulfillmentLocationResolver {

    public Address resolveLocationForFulfillmentGroup(FulfillmentGroup group);

}
