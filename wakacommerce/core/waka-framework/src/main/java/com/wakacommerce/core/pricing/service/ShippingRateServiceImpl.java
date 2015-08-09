
package com.wakacommerce.core.pricing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption;
import com.wakacommerce.core.pricing.dao.ShippingRateDao;
import com.wakacommerce.core.pricing.domain.ShippingRate;
import com.wakacommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;

import java.math.BigDecimal;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blShippingRateService")
@Deprecated
public class ShippingRateServiceImpl implements ShippingRateService {
    
    @Resource(name="blShippingRatesDao")
    protected ShippingRateDao shippingRateDao;

    @Override
    public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity) {
        return shippingRateDao.readShippingRateByFeeTypesUnityQty(feeType, feeSubType, unitQuantity);
    }

    @Override
    public ShippingRate readShippingRateById(Long id) {
        return shippingRateDao.readShippingRateById(id);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public ShippingRate save(ShippingRate shippingRate) {
        return shippingRateDao.save(shippingRate);
    }

}
