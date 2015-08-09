
package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "productOption")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductOptionWrapper extends BaseWrapper implements APIWrapper<ProductOption> {
    
    @XmlElement
    protected String attributeName;

    @XmlElement
    protected String label;

    @XmlElement
    protected Boolean required;
    
    @XmlElement
    protected String productOptionType;
    @XmlElement
    protected String productOptionValidationStrategyType;
    @XmlElement
    protected String productOptionValidationType;
    @XmlElement(name = "allowedValue")
    @XmlElementWrapper(name = "allowedValues")
    protected List<ProductOptionValueWrapper> allowedValues;
    @XmlElement
    protected String validationString;
    
    @Override
    public void wrapDetails(ProductOption model, HttpServletRequest request) {
        this.attributeName = "productOption." + model.getAttributeName();
        this.label = model.getLabel();
        this.required = model.getRequired();
        if (model.getType() != null) {
            this.productOptionType = model.getType().getType();
        }
        if (model.getProductOptionValidationStrategyType() != null) {
            this.productOptionValidationStrategyType = model.getProductOptionValidationStrategyType().getType();
        }
        if (model.getProductOptionValidationType() != null) {
            this.productOptionValidationType = model.getProductOptionValidationType().getType();
        }
        this.validationString = model.getValidationString();
        List<ProductOptionValue> optionValues = model.getAllowedValues();
        if (optionValues != null) {
            ArrayList<ProductOptionValueWrapper> allowedValueWrappers = new ArrayList<ProductOptionValueWrapper>();
            for (ProductOptionValue value : optionValues) {
                ProductOptionValueWrapper optionValueWrapper = (ProductOptionValueWrapper)context.getBean(ProductOptionValueWrapper.class.getName());
                optionValueWrapper.wrapSummary(value, request);
                allowedValueWrappers.add(optionValueWrapper);
            }
            this.allowedValues = allowedValueWrappers;
        }
    }

    @Override
    public void wrapSummary(ProductOption model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getProductOptionType() {
        return productOptionType;
    }

    public void setProductOptionType(String productOptionType) {
        this.productOptionType = productOptionType;
    }

    public String getProductOptionValidationStrategyType() {
        return productOptionValidationStrategyType;
    }

    public void setProductOptionValidationStrategyType(String productOptionValidationStrategyType) {
        this.productOptionValidationStrategyType = productOptionValidationStrategyType;
    }

    public String getProductOptionValidationType() {
        return productOptionValidationType;
    }

    public void setProductOptionValidationType(String productOptionValidationType) {
        this.productOptionValidationType = productOptionValidationType;
    }

    public List<ProductOptionValueWrapper> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(List<ProductOptionValueWrapper> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getValidationString() {
        return validationString;
    }

    public void setValidationString(String validationString) {
        this.validationString = validationString;
    }
}
