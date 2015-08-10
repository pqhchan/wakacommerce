package com.wakacommerce.core.catalog.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.util.WeightUnitOfMeasureType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @ hui
 */
@Embeddable
public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "WEIGHT")
    @AdminPresentation(friendlyName = "Weight_weight", order = 9000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, 
        tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, 
        groupOrder = ProductImpl.Presentation.Group.Order.Shipping)
    protected BigDecimal weight;

        
    @Column(name = "WEIGHT_UNIT_OF_MEASURE")
    @AdminPresentation(friendlyName = "Weight_weightUnitOfMeasure", order = 10000,
        tab = ProductImpl.Presentation.Tab.Name.Shipping, 
        tabOrder = ProductImpl.Presentation.Tab.Order.Shipping,
        group = ProductImpl.Presentation.Group.Name.Shipping, 
        groupOrder = ProductImpl.Presentation.Group.Order.Shipping,
        fieldType= SupportedFieldType.WAKA_ENUMERATION, 
        wakaEnumeration="com.wakacommerce.common.util.WeightUnitOfMeasureType")
    protected String weightUnitOfMeasure;

    public WeightUnitOfMeasureType getWeightUnitOfMeasure() {
        return WeightUnitOfMeasureType.getInstance(weightUnitOfMeasure);
    }

    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure) {
        if (weightUnitOfMeasure != null) {
            this.weightUnitOfMeasure = weightUnitOfMeasure.getType();
        }
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        Weight weight1 = (Weight) o;

        if (weight != null ? !weight.equals(weight1.weight) : weight1.weight != null) return false;
        if (weightUnitOfMeasure != null ? !weightUnitOfMeasure.equals(weight1.weightUnitOfMeasure) : weight1
                .weightUnitOfMeasure != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = weight != null ? weight.hashCode() : 0;
        result = 31 * result + (weightUnitOfMeasure != null ? weightUnitOfMeasure.hashCode() : 0);
        return result;
    }
}
