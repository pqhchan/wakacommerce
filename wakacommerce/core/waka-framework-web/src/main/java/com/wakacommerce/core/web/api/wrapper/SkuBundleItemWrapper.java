
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skuBundleItem")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SkuBundleItemWrapper extends BaseWrapper implements APIWrapper<SkuBundleItem> {
    
    @XmlElement
    protected Long id;
    
    @XmlElement
    protected Integer quantity;
    
    @XmlElement
    protected Money salePrice;
    
    @XmlElement
    protected Money retailPrice;
    
    @XmlElement
    protected Long bundleId;
    
    @XmlElement
    protected SkuWrapper sku;
    
    @XmlElement
    protected String name;

    @XmlElement
    protected Boolean active;

    @XmlElement
    protected String description;

    @XmlElement
    protected String longDescription;
    @XmlElement
    private Long productId;

    @Override
    public void wrapDetails(SkuBundleItem model, HttpServletRequest request) {
        this.id = model.getId();
        this.quantity = model.getQuantity();
        this.salePrice = model.getSalePrice();
        this.retailPrice = model.getRetailPrice();
        this.bundleId = model.getBundle().getId();
        this.name = model.getSku().getName();
        this.description = model.getSku().getDescription();
        this.longDescription = model.getSku().getLongDescription();
        this.active = model.getSku().isActive();
        // this.sku = (SkuWrapper)context.getBean(SkuWrapper.class.getName());
        // this.sku.wrap(model.getSku(), request);
        this.productId = model.getSku().getProduct().getId();
    }

    @Override
    public void wrapSummary(SkuBundleItem model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Money getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }

    public Money getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getBundleId() {
        return bundleId;
    }

    public void setBundleId(Long bundleId) {
        this.bundleId = bundleId;
    }

    public SkuWrapper getSku() {
        return sku;
    }

    public void setSku(SkuWrapper sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
