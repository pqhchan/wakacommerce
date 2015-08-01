
package com.wakacommerce.core.web.api.wrapper;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.Weight;

/**
 * This is a JAXB wrapper around Weight
 * <p/>
 * User:   
 * Date: 4/10/12
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

    
    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    
    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    
    /**
     * @return the unitOfMeasure
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    
    /**
     * @param unitOfMeasure the unitOfMeasure to set
     */
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
