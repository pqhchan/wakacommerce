
package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.core.catalog.service.type.ProductOptionType;
import com.wakacommerce.core.catalog.service.type.ProductOptionValidationStrategyType;
import com.wakacommerce.core.catalog.service.type.ProductOptionValidationType;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemAttribute;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ hui
 */
public interface ProductOption extends Serializable, MultiTenantCloneable<ProductOption> {

    public Long getId();

    public void setId(Long id);

    public ProductOptionType getType();

    public void setType(ProductOptionType type);

    public String getAttributeName();

    public void setAttributeName(String name);

    public String getLabel();

    public void setLabel(String label);

    public Boolean getRequired();

    public void setRequired(Boolean required);

    public Integer getDisplayOrder();

    public void setDisplayOrder(Integer displayOrder);

    @Deprecated
    public List<Product> getProducts();

    @Deprecated
    public void setProducts(List<Product> products);

    public List<ProductOptionXref> getProductXrefs();

    public void setProductXrefs(List<ProductOptionXref> xrefs);

    public List<ProductOptionValue> getAllowedValues();

    public void setAllowedValues(List<ProductOptionValue> allowedValues);

    public Boolean getUseInSkuGeneration();

    public ProductOptionValidationType getProductOptionValidationType();

    public void setProductOptionValidationType(ProductOptionValidationType productOptionValidationType);

    public void setUseInSkuGeneration(Boolean useInSkuGeneration);

    void setErrorMessage(String errorMessage);

    void setErrorCode(String errorCode);

    String getErrorMessage();

    String getValidationString();

    void setValidationString(String validationString);

    String getErrorCode();

    void setProductOptionValidationStrategyType(ProductOptionValidationStrategyType productOptionValidationType);

    ProductOptionValidationStrategyType getProductOptionValidationStrategyType();

}
