
package com.wakacommerce.core.pricing.dao;

import java.math.BigDecimal;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption;
import com.wakacommerce.core.pricing.domain.ShippingRate;
import com.wakacommerce.core.pricing.service.FulfillmentPricingService;
import com.wakacommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;

/**
 *
 * @ hui
 */
@Deprecated
public interface ShippingRateDao {

    public ShippingRate save(ShippingRate shippingRate);

    public ShippingRate readShippingRateById(Long id);

    public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity);

    public ShippingRate create();

}
