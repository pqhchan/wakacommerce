
package com.wakacommerce.core.pricing.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption;
import com.wakacommerce.core.pricing.domain.ShippingRate;
import com.wakacommerce.core.pricing.domain.ShippingRateImpl;
import com.wakacommerce.core.pricing.service.FulfillmentPricingService;
import com.wakacommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * @deprecated Superceded in functionality by {@link BandedPriceFulfillmentOption} and {@link BandedFulfillmentPricingProvider}
 * @see {@link FulfillmentOption}, {@link FulfillmentPricingService}
 */
@Repository("blShippingRatesDao")
@Deprecated
public class ShippingRateDaoImpl implements ShippingRateDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public ShippingRate save(ShippingRate shippingRate) {
        return em.merge(shippingRate);
    }

    @Override
    public ShippingRate readShippingRateById(Long id) {
        return em.find(ShippingRateImpl.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity) {
        Query query = em.createNamedQuery("BC_READ_FIRST_SHIPPING_RATE_BY_FEE_TYPES");
        query.setParameter("feeType", feeType);
        query.setParameter("feeSubType", feeSubType);
        query.setParameter("bandUnitQuantity", unitQuantity);
        List<ShippingRate> returnedRates = query.getResultList();
        if (returnedRates.size() > 0) {
            return returnedRates.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ShippingRate create() {
        return (ShippingRate) entityConfiguration.createEntityInstance(ShippingRate.class.getName());
    }
}
