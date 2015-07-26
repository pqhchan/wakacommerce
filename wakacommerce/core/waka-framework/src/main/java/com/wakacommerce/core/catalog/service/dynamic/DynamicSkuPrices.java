
package com.wakacommerce.core.catalog.service.dynamic;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

/**
 * DTO to represent pricing overrides returned from invocations to {@link DynamicSkuPricingService}
 *jfischer
 * @see {@link DynamicSkuPricingService}
 */
public class DynamicSkuPrices implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Money retailPrice;
    protected Money salePrice;
    protected Money priceAdjustment;

    public Money getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Money getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }

    public Money getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(Money priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    /**
     * The out of box implementation returns {@link #getPrice()}.   Intended as a hook for
     * advanced pricing considerations like those in BLC Enterprise pricing.
     * 
     * @param quantity
     * @param currentPrice
     * @return
     */
    public Money getPriceForQuantity(long quantity) {
        return getPrice();
    }

    /**
     * Returns {@link #getSalePrice()}.  Intended as a hook for
     * advanced pricing considerations like those in BLC Enterprise pricing.
     * @return
     */
    public Money getPrice() {
        return getSalePrice();
    }

}
