
package com.wakacommerce.core.catalog.service.dynamic;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

/**
 *
 * @ hui
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

    public Money getPriceForQuantity(long quantity) {
        return getPrice();
    }

    public Money getPrice() {
        return getSalePrice();
    }

}
