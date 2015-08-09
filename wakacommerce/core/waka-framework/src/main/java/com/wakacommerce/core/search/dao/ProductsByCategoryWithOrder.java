
package com.wakacommerce.core.search.dao;

import java.math.BigDecimal;


/**
 *
 * @ hui
 */
public class ProductsByCategoryWithOrder {

    protected Long productId;
    protected BigDecimal displayOrder;

    public ProductsByCategoryWithOrder(Long productId, BigDecimal displayOrder) {
        this.productId = productId;
        this.displayOrder = displayOrder;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public BigDecimal getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder = displayOrder;
    }
    
}
