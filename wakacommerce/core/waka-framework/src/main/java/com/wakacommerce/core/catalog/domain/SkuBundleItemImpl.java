
package com.wakacommerce.core.catalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.util.ClassUtils;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationToOneLookup;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.RequiredOverride;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.core.catalog.service.dynamic.DefaultDynamicSkuPricingInvocationHandler;
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import java.lang.reflect.Proxy;
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
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_BUNDLE_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blProducts")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.FALSE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class SkuBundleItemImpl implements SkuBundleItem {

    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(generator = "SkuBundleItemId")
    @GenericGenerator(
        name="SkuBundleItemId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name = "segment_value", value = "SkuBundleItemImpl"),
            @Parameter(name = "entity_name", value = "com.wakacommerce.core.catalog.domain.SkuBundleItemImpl")
        }
    )
    @Column(name = "SKU_BUNDLE_ITEM_ID")
    @AdminPresentation(friendlyName = "SkuBundleItemImpl_ID", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Column(name = "QUANTITY", nullable=false)
    @AdminPresentation(friendlyName = "bundleItemQuantity", prominent = true,
        requiredOverride = RequiredOverride.REQUIRED)
    protected Integer quantity;

    @Column(name = "ITEM_SALE_PRICE", precision=19, scale=5)
    @AdminPresentation(friendlyName = "bundleItemSalePrice", prominent = true,
        tooltip="bundleItemSalePriceTooltip", 
        fieldType = SupportedFieldType.MONEY)
    protected BigDecimal itemSalePrice;

    @ManyToOne(targetEntity = ProductBundleImpl.class, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_BUNDLE_ID", referencedColumnName = "PRODUCT_ID")
    protected ProductBundle bundle;

    @ManyToOne(targetEntity = SkuImpl.class, optional = false)
    @JoinColumn(name = "SKU_ID", referencedColumnName = "SKU_ID")
    @AdminPresentation(friendlyName = "Sku", prominent = true, 
        order = 0, gridOrder = 0)
    @AdminPresentationToOneLookup()
    protected Sku sku;

    @Transient
    protected DynamicSkuPrices dynamicPrices = null;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
   
    public Money getDynamicSalePrice(Sku sku, BigDecimal salePrice) {   
        Money returnPrice = null;
        
        if (SkuPricingConsiderationContext.hasDynamicPricing()) {
            if (dynamicPrices != null) {
                returnPrice = dynamicPrices.getSalePrice();
            } else {
                DefaultDynamicSkuPricingInvocationHandler handler = new DefaultDynamicSkuPricingInvocationHandler(sku, salePrice);
                Sku proxy = (Sku) Proxy.newProxyInstance(sku.getClass().getClassLoader(), ClassUtils.getAllInterfacesForClass(sku.getClass()), handler);
                dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getSkuPrices(proxy, SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
                returnPrice = dynamicPrices.getSalePrice();
            }
        } else {
            if (salePrice != null) {
                returnPrice = new Money(salePrice,Money.defaultCurrency());
            }
        }
        
        return returnPrice;   
    }
    @Override
    public void setSalePrice(Money salePrice) {
        if (salePrice != null) {
            this.itemSalePrice = salePrice.getAmount();
        } else {
            this.itemSalePrice = null;
        }
    }


    @Override
    public Money getSalePrice() {
        if (itemSalePrice == null) {
            return sku.getSalePrice();
        } else {
            return getDynamicSalePrice(sku, itemSalePrice);
        }
    }

    @Override
    public Money getRetailPrice() {
         return sku.getRetailPrice();
     }

    @Override
    public ProductBundle getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(ProductBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Sku getSku() {
        return sku;
    }

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }
    
    @Override
    public void clearDynamicPrices() {
        this.dynamicPrices = null;
    }

    @Override
    public <G extends SkuBundleItem> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        SkuBundleItem cloned = createResponse.getClone();
        cloned.setQuantity(quantity);
        cloned.setSalePrice(getSalePrice());
        if (sku != null) {
            cloned.setSku(sku.createOrRetrieveCopyInstance(context).getClone());
        }
        if (bundle != null) {
            cloned.setBundle((ProductBundle) bundle.createOrRetrieveCopyInstance(context).getClone());
        }
        return createResponse;
    }
}
