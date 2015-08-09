
package com.wakacommerce.core.search.dao;


/**
 *
 * @ hui
 */
public class ParentCategoryByProduct {

    protected Long parent;
    protected Long product;

    public ParentCategoryByProduct(Long parent, Long product) {
        this.parent = parent;
        this.product = product;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
    
}
