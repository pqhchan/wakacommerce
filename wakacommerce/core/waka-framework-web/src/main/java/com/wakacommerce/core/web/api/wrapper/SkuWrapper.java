

package com.wakacommerce.core.web.api.wrapper;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.util.xml.ISO8601DateAdapter;
import com.wakacommerce.core.catalog.domain.Sku;

/**
 * This is a JAXB wrapper to wrap Sku.
 * <p/>
 * User:   
 * Date: 4/10/12
 */
@XmlRootElement(name = "sku")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SkuWrapper extends BaseWrapper implements APIWrapper<Sku> {

    @XmlElement
    protected Long id;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeStartDate;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeEndDate;

    @XmlElement
    protected String name;

    @XmlElement
    protected Boolean active;

    @XmlElement
    protected String inventoryType;

    @XmlElement
    protected String description;

    @XmlElement
    protected Money retailPrice;

    @XmlElement
    protected Money salePrice;

    @XmlElement
    protected WeightWrapper weight;

    @XmlElement
    protected DimensionWrapper dimension;

    @Override
    public void wrapDetails(Sku model, HttpServletRequest request) {
        this.id = model.getId();
        this.activeStartDate = model.getActiveStartDate();
        this.activeEndDate = model.getActiveEndDate();
        this.name = model.getName();
        this.description = model.getDescription();
        this.retailPrice = model.getRetailPrice();
        this.salePrice = model.getSalePrice();
        this.active = model.isActive();
        if (model.getInventoryType() != null) {
            this.inventoryType = model.getInventoryType().getType();
        }

        if (model.getWeight() != null) {
            weight = (WeightWrapper) context.getBean(WeightWrapper.class.getName());
            weight.wrapDetails(model.getWeight(), request);
        }

        if (model.getDimension() != null) {
            dimension = (DimensionWrapper) context.getBean(DimensionWrapper.class.getName());
            dimension.wrapDetails(model.getDimension(), request);
        }
    }

    @Override
    public void wrapSummary(Sku model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(Date activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public Date getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(Date activeEndDate) {
        this.activeEndDate = activeEndDate;
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

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public WeightWrapper getWeight() {
        return weight;
    }

    public void setWeight(WeightWrapper weight) {
        this.weight = weight;
    }

    public DimensionWrapper getDimension() {
        return dimension;
    }

    public void setDimension(DimensionWrapper dimension) {
        this.dimension = dimension;
    }
}
