
package com.wakacommerce.core.catalog.domain;

public class RelatedProductDTO {
    private Long categoryId;
    private Long productId;
    private RelatedProductTypeEnum type = RelatedProductTypeEnum.FEATURED;
    private boolean cumulativeResults=true;
    private Integer quantity = null;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }
 
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public RelatedProductTypeEnum getType() {
        return type;
    }

    public void setType(RelatedProductTypeEnum type) {
        this.type = type;
    }

    public boolean isCumulativeResults() {
        return cumulativeResults;
    }

    public void setCumulativeResults(boolean cumulativeResults) {
        this.cumulativeResults = cumulativeResults;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
