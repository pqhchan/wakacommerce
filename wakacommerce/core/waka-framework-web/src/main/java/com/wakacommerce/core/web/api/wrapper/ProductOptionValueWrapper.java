
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productOptionAllowedValue")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductOptionValueWrapper extends BaseWrapper implements
        APIWrapper<ProductOptionValue> {
    
    @XmlElement
    protected String attributeValue;
    
    @XmlElement
    protected Money priceAdjustment;
    
    @XmlElement
    protected Long productOptionId;
    
    @Override
    public void wrapDetails(ProductOptionValue model, HttpServletRequest request) {
        this.attributeValue = model.getAttributeValue();
        this.priceAdjustment = model.getPriceAdjustment();
        this.productOptionId = model.getProductOption().getId();
    }

    @Override
    public void wrapSummary(ProductOptionValue model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Money getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(Money priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }
}
