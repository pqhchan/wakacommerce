
package com.wakacommerce.core.catalog.service.dynamic;

import java.util.Date;

import javax.annotation.Nonnull;

import com.wakacommerce.core.catalog.domain.Sku;

/**
 *
 * @ hui
 */
public interface DynamicSkuActiveDatesService {

    @Nonnull
    @SuppressWarnings("rawtypes")
    public Date getDynamicSkuActiveStartDate(Sku sku);

    @SuppressWarnings("rawtypes")
    public Date getDynamicSkuActiveEndDate(Sku sku);
}
