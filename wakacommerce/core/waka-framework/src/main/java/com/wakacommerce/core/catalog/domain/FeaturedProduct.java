package com.wakacommerce.core.catalog.domain;

import java.math.BigDecimal;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface FeaturedProduct extends PromotableProduct, MultiTenantCloneable<FeaturedProduct> {

    Long getId();

    void setId(Long id);

    Category getCategory();

    void setCategory(Category category);

    Product getProduct();

    void setProduct(Product product);

    void setSequence(BigDecimal sequence);
    
    BigDecimal getSequence();

    String getPromotionMessage();

    void setPromotionMessage(String promotionMessage);

    Product getRelatedProduct();

}
