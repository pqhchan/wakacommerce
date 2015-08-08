package com.wakacommerce.core.catalog.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_OPTION_VALUE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blProducts")
@AdminPresentationClass(friendlyName = "Product Option Value")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class ProductOptionValueImpl implements ProductOptionValue {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ProductOptionValueId")
    @GenericGenerator(
        name = "ProductOptionValueId",
        strategy = "com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
                @Parameter(name = "segment_value", value = "ProductOptionValueImpl"),
                @Parameter(name = "entity_name", value = "com.wakacommerce.core.catalog.domain.ProductOptionValueImpl")
        })
    @Column(name = "PRODUCT_OPTION_VALUE_ID")
    protected Long id;

    @Column(name = "ATTRIBUTE_VALUE")
    @AdminPresentation(friendlyName = "productOptionValue_attributeValue", 
            prominent = true, order = Presentation.FieldOrder.ATTRIBUTE_VALUE,
            gridOrder = Presentation.FieldOrder.ATTRIBUTE_VALUE)
    protected String attributeValue;

    @Column(name = "DISPLAY_ORDER")
    @AdminPresentation(friendlyName = "productOptionValue_displayOrder", prominent = true,
            gridOrder = Presentation.FieldOrder.DISPLAY_ORDER, order = Presentation.FieldOrder.DISPLAY_ORDER)
    protected Long displayOrder;

    @Column(name = "PRICE_ADJUSTMENT", precision = 19, scale = 5)
    @AdminPresentation(friendlyName = "productOptionValue_adjustment", fieldType = SupportedFieldType.MONEY,
            prominent = true, gridOrder = Presentation.FieldOrder.PRICE_ADJUSTMENT, order = Presentation.FieldOrder.PRICE_ADJUSTMENT)
    protected BigDecimal priceAdjustment;

    @ManyToOne(targetEntity = ProductOptionImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_OPTION_ID")
    protected ProductOption productOption;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAttributeValue() {
        return DynamicTranslationProvider.getValue(this, "attributeValue", attributeValue);
    }

    @Override
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public Long getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public Money getPriceAdjustment() {

        Money returnPrice = null;

        if (SkuPricingConsiderationContext.hasDynamicPricing()) {

            DynamicSkuPrices dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getPriceAdjustment(this, priceAdjustment == null ? null : new Money(priceAdjustment), SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
            returnPrice = dynamicPrices.getPriceAdjustment();

        } else {
            if (priceAdjustment != null) {
                returnPrice = new Money(priceAdjustment, Money.defaultCurrency());
            }
        }

        return returnPrice;
    }

    @Override
    public void setPriceAdjustment(Money priceAdjustment) {
        this.priceAdjustment = Money.toAmount(priceAdjustment);
    }

    @Override
    public ProductOption getProductOption() {
        return productOption;
    }

    @Override
    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        ProductOptionValueImpl other = (ProductOptionValueImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (getAttributeValue() == null) {
            if (other.getAttributeValue() != null) {
                return false;
            }
        } else if (!getAttributeValue().equals(other.getAttributeValue())) {
            return false;
        }
        return true;
    }

    @Override
    public <G extends ProductOptionValue> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        ProductOptionValue cloned = createResponse.getClone();
        cloned.setAttributeValue(attributeValue);
        cloned.setDisplayOrder(displayOrder);
        cloned.setPriceAdjustment(getPriceAdjustment());
        if (productOption != null) {
            cloned.setProductOption(productOption.createOrRetrieveCopyInstance(context).getClone());
        }
        
        return  createResponse;
    }

    public static class Presentation {

        public static class FieldOrder {

            public static final int ATTRIBUTE_VALUE = 1000;
            public static final int DISPLAY_ORDER = 3000;
            public static final int PRICE_ADJUSTMENT = 2000;
        }
    }

}
