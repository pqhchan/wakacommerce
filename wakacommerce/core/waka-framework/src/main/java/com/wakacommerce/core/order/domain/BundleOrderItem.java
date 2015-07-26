
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.Sku;

import java.util.List;


public interface BundleOrderItem extends OrderItem, OrderItemContainer, SkuAccessor {

    List<DiscreteOrderItem> getDiscreteOrderItems();

    void setDiscreteOrderItems(List<DiscreteOrderItem> discreteOrderItems);

    Money getTaxablePrice();
    
    public List<BundleOrderItemFeePrice> getBundleOrderItemFeePrices();

    public void setBundleOrderItemFeePrices(List<BundleOrderItemFeePrice> bundleOrderItemFeePrices);

    public boolean hasAdjustedItems();

    public Money getBaseRetailPrice();

    public void setBaseRetailPrice(Money baseRetailPrice);

    public Money getBaseSalePrice();

    public void setBaseSalePrice(Money baseSalePrice);

    /**
     * For BundleOrderItem created from a ProductBundle, this will represent the default sku of
     * the product bundle.
     *
     * This can be null for implementations that programatically create product bundles.
     *
     * @return
     */
    Sku getSku();

    void setSku(Sku sku);

    /**
     * Returns the associated ProductBundle or null if not applicable.
     *
     * If null, then this ProductBundle was manually created.
     *
     * @return
     */
    ProductBundle getProductBundle();

    /**
     * Sets the ProductBundle associated with this BundleOrderItem.
     *
     * @param bundle
     */
    void setProductBundle(ProductBundle bundle);

    /**
     * Same as getProductBundle.
     */
    Product getProduct();

    public boolean shouldSumItems();
}
