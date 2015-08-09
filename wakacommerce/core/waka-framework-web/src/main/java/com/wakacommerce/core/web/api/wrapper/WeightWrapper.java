
package com.wakacommerce.core.web.api.wrapper;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.Weight;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "weight")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class WeightWrapper implements APIWrapper<Weight>{

    @XmlElement
    protected BigDecimal weight;

    @XmlElement
    protected String unitOfMeasure;

    @Override
    public void wrapDetails(Weight model, HttpServletRequest request) {
        this.weight = model.getWeight();
        if (model.getWeightUnitOfMeasure() != null) {
            this.unitOfMeasure = model.getWeightUnitOfMeasure().getType();
        }
    }

    @Override
    public void wrapSummary(Weight model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
