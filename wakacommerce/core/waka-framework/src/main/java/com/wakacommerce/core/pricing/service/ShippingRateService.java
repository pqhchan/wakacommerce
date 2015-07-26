
package com.wakacommerce.core.pricing.service;

import java.math.BigDecimal;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption;
import com.wakacommerce.core.pricing.domain.ShippingRate;
import com.wakacommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;

/**
 * @deprecated Superceded in functionality by {@link BandedPriceFulfillmentOption} and {@link BandedFulfillmentPricingProvider}
 * @see {@link FulfillmentOption}, {@link FulfillmentPricingService}
 */
@Deprecated
public interface ShippingRateService {

    public ShippingRate save(ShippingRate shippingRate);

    public ShippingRate readShippingRateById(Long id);

    public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity);

}
