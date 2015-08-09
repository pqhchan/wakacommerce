
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import java.util.List;
import java.util.Map;

public interface DiscreteOrderItem extends OrderItem, SkuAccessor, Cloneable {

    Sku getSku();

    void setSku(Sku sku);

    Product getProduct();

    void setProduct(Product product);

    BundleOrderItem getBundleOrderItem();

    void setBundleOrderItem(BundleOrderItem bundleOrderItem);

    SkuBundleItem getSkuBundleItem();

    void setSkuBundleItem(SkuBundleItem skuBundleItem);

    Money getTaxablePrice();

    public Map<String, String> getAdditionalAttributes();

    public void setAdditionalAttributes(Map<String, String> additionalAttributes);

    public Money getBaseRetailPrice();

    public void setBaseRetailPrice(Money baseRetailPrice);

    public Money getBaseSalePrice();

    public void setBaseSalePrice(Money baseSalePrice);
    
    public List<DiscreteOrderItemFeePrice> getDiscreteOrderItemFeePrices();

    public void setDiscreteOrderItemFeePrices(List<DiscreteOrderItemFeePrice> orderItemFeePrices);

    public BundleOrderItem findParentItem();

    public boolean isSkuActive();
}
