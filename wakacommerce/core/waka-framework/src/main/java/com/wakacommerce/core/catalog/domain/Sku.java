package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import com.wakacommerce.core.inventory.service.InventoryService;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.order.service.workflow.CheckAvailabilityActivity;

/**
 * @see {@link SkuImpl}, {@link Money}
 * @author hui
 */
public interface Sku extends Serializable, MultiTenantCloneable<Sku> {

    public Long getId();
    public void setId(Long id);

    public String getUrlKey();
    public void setUrlKey(String url);

    public String getDisplayTemplate();
    public void setDisplayTemplate(String displayTemplate);
    
    /**
     * 可以是null
     * @see {@link ProductOptionValue}
     */
    public Money getProductOptionValueAdjustments();
    
    /**
     * 销售价格（标准销售价格）
     * @see SkuPricingConsiderationContext, DynamicSkuPricingService
     */
    public Money getSalePrice();
    public void setSalePrice(Money salePrice);
    public boolean hasSalePrice();

    /**
     * 零售价格（建议零售价）
     */
    public Money getRetailPrice();
    public void setRetailPrice(Money retailPrice);
    public boolean hasRetailPrice();

    /**
     * 该Sku的售价。如果Sku在售的话(isOnSale返回true)，则该方法返回的是getSalePrice()。
     * 否则，返回getRetailPrice()。
     */
    public Money getPrice();

    public String getName();
    public void setName(String name);

    public String getDescription();
    public void setDescription(String description);
    public String getLongDescription();
    public void setLongDescription(String longDescription);

    /**
     * Returns whether the Sku qualifies for taxes or not.  This field is used by the pricing engine
     * to calculate taxes.
     */
    public Boolean isTaxable();

    /**
     * Returns whether the Sku qualifies for discounts or not.  This field is used by the pricing engine
     * to apply offers.
     */
    public Boolean isDiscountable();

    /**
     * Sets the whether the Sku qualifies for discounts or not.  This field is used by the pricing engine
     * to apply offers.
     */
    public void setDiscountable(Boolean discountable);

    /**
     * <p>Availability is really a concern of inventory vs a concern of the Sku being active or not. A Sku could be marked as
     * unavailable but still be considered 'active' where you still want to show the Sku on the site but not actually sell
     * it. This defaults to true</p>
     * 
     * <p>This method only checks that this Sku is not marked as {@link InventoryType#UNAVAILABLE}. If {@link #getInventoryType()}
     * is set to {@link InventoryType#CHECK_QUANTITY} then this will return true.</p>
     * 
     * @deprecated use {@link #getInventoryType()} or {@link InventoryService#isAvailable(Sku, int)} instead.
     */
    @Deprecated
    public Boolean isAvailable();

    /**
     * Convenience that passes through to isAvailable
     * @see {@link #isAvailable()}
     * @deprecated use {@link #getInventoryType()} instead
     */
    @Deprecated
    public Boolean getAvailable();
    
    /**
     * Availability is really a concern of inventory vs a concern of the Sku being active or not. A Sku could be marked as
     * unavailable but still be considered 'active' where you still want to show the Sku on the site but not actually sell
     * it. This defaults to true
     * 
     * @deprecated use {@link #setInventoryType(InventoryType)} instead
     */
    @Deprecated
    public void setAvailable(Boolean available);

    /**
     * Returns the first date that the Sku should be available for sale.  This field is used to determine
     * whether a user can add the sku to their cart.
     */
    public Date getActiveStartDate();

    /**
     * Sets the the first date that the Sku should be available for sale.  This field is used to determine
     * whether a user can add the sku to their cart.
     */
    public void setActiveStartDate(Date activeStartDate);

    /**
     * Returns the the last date that the Sku should be available for sale.  This field is used to determine
     * whether a user can add the sku to their cart.
     */
    public Date getActiveEndDate();

    /**
     * Sets the the last date that the Sku should be available for sale.  This field is used to determine
     * whether a user can add the sku to their cart.
     */
    public void setActiveEndDate(Date activeEndDate);

    /**
     * Get the dimensions for this Sku
     * 
     * @return this Sku's embedded Weight
     */
    public Dimension getDimension();

    /**
     * Sets the embedded Dimension for this Sku
     * 
     * @param dimension
     */
    public void setDimension(Dimension dimension);

    /**
     * Gets the embedded Weight for this Sku
     * 
     * @return this Sku's embedded Weight
     */
    public Weight getWeight();

    /**
     * Sets the embedded Weight for this Sku
     * 
     * @param weight
     */
    public void setWeight(Weight weight);
    
    /**
     * Returns a boolean indicating whether this sku is active.  This is used to determine whether a user
     * the sku can add the sku to their cart.
     */
    public boolean isActive();

    /**
     * Returns a map of key/value pairs where the key is a string for the name of a media object and the value
     * is a media object.
     * @deprecated use {@link #getSkuMediaXref()} instead
     */
    @Deprecated
    public Map<String, Media> getSkuMedia();

    /**
     * Sets a map of key/value pairs where the key is a string for the name of a media object and the value
     * is an object of type Media.
     * @deprecated use {@link #setSkuMediaXref(java.util.Map)} instead
     */
    @Deprecated
    public void setSkuMedia(Map<String, Media> skuMedia);

    /**
     * Returns a map of key/value pairs where the key is a string for the name of a media object and the value
     * is a cross-reference to a media object.
     */
    Map<String, SkuMediaXref> getSkuMediaXref();

    /**
     * Sets a map of key/value pairs where the key is a string for the name of a media object and the value
     * is a cross-reference object to type Media.
     */
    void setSkuMediaXref(Map<String, SkuMediaXref> skuMediaXref);

    /**
     * Returns whether or not this Sku, the given Product and the given Category are all active
     * 
     * @param product - Product that should be active
     * @param category - Category that should be active
     * @return <b>true</b> if this Sku, <code>product</code> and <code>category</code> are all active
     * <b>false</b> otherwise
     */
    public boolean isActive(Product product, Category category);

    /**
     * Denormalized set of key-value pairs to attach to a Sku. If you are looking for setting up
     * a {@link ProductOption} scenario (like colors, sizes, etc) see {@link getProductOptionValues()}
     * and {@link setProductOptionValues()}
     *
     * @return the attributes for this Sku
     */
    public Map<String, SkuAttribute> getSkuAttributes();

    /**
     * Sets the denormalized set of key-value pairs on a Sku
     *
     * @param skuAttributes
     */
    public void setSkuAttributes(Map<String, SkuAttribute> skuAttributes);

    /**
     * Gets the ProductOptionValues used to map to this Sku. For instance, this Sku could hold specific
     * inventory, price and image information for a "Blue" "Extra-Large" shirt
     * 
     * @return the ProductOptionValues for this Sku
     * @see {@link ProductOptionValue}, {@link ProductOption}
     * @deprecated use {@link #getProductOptionValuesCollection()} instead
     */
    @Deprecated
    public List<ProductOptionValue> getProductOptionValues();

    /**
     * Sets the ProductOptionValues that should be mapped to this Sku
     * 
     * @param productOptionValues
     * @see {@link ProductOptionValue}, {@link ProductOption}
     * @deprecated use {@link #setProductOptionValuesCollection(java.util.Set)} instead
     */
    @Deprecated
    public void setProductOptionValues(List<ProductOptionValue> productOptionValues);

    /**
     * Gets the ProductOptionValues used to map to this Sku. For instance, this Sku could hold specific
     * inventory, price and image information for a "Blue" "Extra-Large" shirt
     *
     * @return the ProductOptionValues for this Sku
     * @see {@link ProductOptionValue}, {@link ProductOption}
     * @deprecated use {@link #getProductOptionValueXrefs()} instead
     */
    Set<ProductOptionValue> getProductOptionValuesCollection();

    /**
     * Sets the ProductOptionValues that should be mapped to this Sku
     *
     * @param productOptionValues
     * @see {@link ProductOptionValue}, {@link ProductOption}
     * @deprecated use {@link #setProductOptionValueXrefs(Set)} instead
     */
    void setProductOptionValuesCollection(Set<ProductOptionValue> productOptionValues);

    /**
     * Returns the ProductOptionValues that should be mapped to this Sku using the middle 
     * XREF entity, {@link SkuProductOptionValueXref}
     * 
     * @return the Set of {@link SkuProductOptionValueXref}s
     */
    public Set<SkuProductOptionValueXref> getProductOptionValueXrefs();

    /**
     * Sets the ProductOptionValues that should be mapped to this Sku using the middle 
     * XREF entity, {@link SkuProductOptionValueXref}
     */
    public void setProductOptionValueXrefs(Set<SkuProductOptionValueXref> productOptionValueXrefs);


    /**
     * This will be a value if and only if this Sku is the defaultSku of a Product (and thus has a @OneToOne relationship with a Product).
     * The mapping for this is actually done at the Product level with a foreign key to Sku; this exists for convenience to get the reverse relationship
     * 
     * @return The associated Product if this Sku is a defaultSku, <b>null</b> otherwise
     * @see #getProduct()
     */
    public Product getDefaultProduct();

    /**
     * The relationship for a Product's default Sku (and thus a Sku's default Product) is actually maintained
     * on the Product entity as a foreign key to Sku.  Because of this, there are probably very few circumstances
     * that you would actually want to change this from the Sku perspective instead of the Product perspective.
     * <br />
     * <br />
     * If you are looking for a way to simply associate a Sku to a Product, the correct way would be to call
     * {@link #setProduct(Product)} or {@link Product#setSkus(List<Sku>)} which would then cause this Sku to show up in the list of Skus for
     * the given Product
     * 
     * @param product
     */
    public void setDefaultProduct(Product product);

    /**
     * This will return the correct Product association that is being used on the Sku. If this Sku is a default Sku
     * for a Product (as in, {@link #getDefaultProduct()} != null) than this will return {@link #getDefaultProduct()}. If this is not
     * a default Sku for a Product, this will return the @ManyToOne Product relationship created by adding this Sku to a Product's
     * list of Skus, or using {@link setProduct(Product)}.
     * <br />
     * <br />
     * In some implementations, it might make sense to have both the @OneToOne association set ({@link Product#setDefaultSku(Sku)})
     * as well as the @ManyToOne association set ({@link #setProduct(Product)}). In this case, This method would only return
     * the result of {@link #getDefaultProduct()}.  However, the @OneToOne and @ManyToOne association should never actually
     * refer to different Products, and would represent an error state. If you require this, consider subclassing and using
     * your own @ManyToMany relationship between Product and Sku. If you are trying to model bundles, consider using a {@link ProductBundle}
     * and subsequent {@link SkuBundleItem}s.
     * 
     * @return {@link #getDefaultProduct()} if {@link #getDefaultProduct()} is non-null, the @ManyToOne Product association otherwise. If no
     * relationship is set, returns null
     */
    public Product getProduct();

    /**
     * Associates a Sku to a given Product. This will then show up in the list returned by {@link Product#getSkus()}
     * 
     * @param product - Product to associate this Sku to
     * @see Product#getSkus()
     */
    public void setProduct(Product product);

    /**
     * A product is on sale provided the sale price is not null, non-zero, and less than the retail price.
     * 
     * Note that this flag should always be checked before showing or using a sale price as it is possible 
     * for a sale price to be greater than the retail price from a purely data perspective.
     * 
     * @return whether or not the product is on sale
     */
    public boolean isOnSale();

    /**
     * Whether this Sku can be sorted by a machine
     * 
     * @return <b>true</b> if this Sku can be sorted by a machine
     * @deprecated use {@link #getIsMachineSortable()} instead since that is the correct bean notation
     */
    @Deprecated
    public Boolean isMachineSortable();

    /**
     * Whether this Sku can be sorted by a machine
     * 
     */
    public Boolean getIsMachineSortable();

    /**
     * Sets whether or not this Sku can be sorted by a machine
     * 
     * @param isMachineSortable
     * @deprecated use {@link #setIsMachineSortable(Boolean)} instead since that is the correct bean notation
     */
    @Deprecated
    public void setMachineSortable(Boolean isMachineSortable);
    
    /**
     * Sets whether or not this Sku can be sorted by a machine
     * @param isMachineSortable
     */
    public void setIsMachineSortable(Boolean isMachineSortable);

    /**
     * Gets all the extra fees for this particular Sku. If the fee type is FULFILLMENT, these are stored
     * on {@link FulfillmentGroup#getFulfillmentGroupFees()} for an Order
     * 
     * @return the {@link SkuFee}s for this Sku
     */
    public List<SkuFee> getFees();

    /**
     * Sets the extra fees for this particular Sku
     * 
     * @param fees
     */
    public void setFees(List<SkuFee> fees);

    /**
     * Gets the flat rate for fulfilling this {@link Sku} for a particular {@link FulfillmentOption}. Depending
     * on the result of {@link FulfillmentOption#getUseFlatRates()}, this flat rate will be used in calculating
     * the cost of fulfilling this {@link Sku}.
     * 
     * @return the flat rates for this {@link Sku}
     */
    public Map<FulfillmentOption, BigDecimal> getFulfillmentFlatRates();

    /**
     * Sets the flat rates for fulfilling this {@link Sku} for a particular {@link FulfillmentOption}. Depending
     * on the result of {@link FulfillmentOption#getUseFlatRates()}, this flat rate will be used in calculating
     * the cost of fulfilling this {@link Sku}.
     * 
     * @param fulfillmentFlatRates
     */
    public void setFulfillmentFlatRates(Map<FulfillmentOption, BigDecimal> fulfillmentFlatRates);

    /**
     * Gets the {@link FulfillmentOption}s that this {@link Sku} should be excluded from. For instance,
     * some {@link Sku}s might not be available to be fulfilled next-day
     * 
     * @return
     */
    public List<FulfillmentOption> getExcludedFulfillmentOptions();

    /**
     * Sets the {@link FulfillmentOption}s that this Sku should be excluded from being apart of
     * 
     * @param excludedFulfillmentOptions
     */
    public void setExcludedFulfillmentOptions(List<FulfillmentOption> excludedFulfillmentOptions);

    /**
     * Returns the type of inventory for this sku
     * @return the {@link com.wakacommerce.core.inventory.service.type.InventoryType} for this sku
     */
    public InventoryType getInventoryType();

    /**
     * Sets the type of inventory for this sku
     * @param inventoryType the {@link InventoryType} for this sku
     */
    public void setInventoryType(InventoryType inventoryType);
    
    /**
     * <p>Used in conjuction with {@link InventoryType#CHECK_QUANTITY} within the blAddItemWorkflow and blUpdateItemWorkflow.
     * This field is checked within the {@link CheckAvailabilityActivity} to determine if inventory is actually available
     * for this Sku.
     */
    public Integer getQuantityAvailable();
    
    /**
     * <p>Used in conjunction with {@link InventoryType#CHECK_QUANTITY} from {@link #getInventoryType()}. This sets how much
     * inventory is available for this Sku.</p>
     * 
     * 
     * @param quantityAvailable the quantity available for this sku 
     */
    public void setQuantityAvailable(Integer quantityAvailable);
    
    /**
     * Returns the fulfillment type for this sku. May be null.
     * @return
     */
    public FulfillmentType getFulfillmentType();
    
    /**
     * Sets the fulfillment type for this sku. May return null.
     * @param fulfillmentType
     */
    public void setFulfillmentType(FulfillmentType fulfillmentType);

    /**
     * Clears any currently stored dynamic pricing
     */
    public void clearDynamicPrices();

    /**
     * Sets the currency for this Sku
     * 
     * Note: Currency is ignored when using dynamic pricing
     * 
     * @param currency
     */
    public void setCurrency(BroadleafCurrency currency);

    /**
     * <b>Note: When using dynamic pricing, this method is unreliable and should not be called outside of the 
     * Broadleaf admin</b>  Instead, you should rely on the {@link WakaRequestContext#getBroadleafCurrency()} 
     * instead of storing at the SKU level.
     * 
     * As such, for supported, enterprise installations, this method should always return null.
     * 
     * This method was not deprecated as it may have some use in non-standard Broadleaf installations but
     * using its use is not suggested for most implementations. 
     * 
     * @return the currency for this sku
     */
    public BroadleafCurrency getCurrency();

    /**
     * Gets the Universal Product Code (UPC)
     * @return
     */
    public String getUpc();

    /**
     * Sets the Universal Product Code (UPC)
     * @param upc
     */
    public void setUpc(String upc);

    /**
     * Intended to hold any unique identifier not tied to the Broadleaf Database Sequence Identifier.
     * For example, many implementations may integrate or import/export
     * data from other systems that manage their own unique identifiers.
     *
     * @return external ID
     */
    public String getExternalId();

    /**
     * Sets a unique external ID
     * @param externalId
     */
    public void setExternalId(String externalId);

    /**
     * If a DynamicPricingService is being used, this method will return the dynamic Sku prices.
     * Otherwise, it will return an instance of DynamicSkuPrices with the retail and sale price
     * from the underlying record.
     * 
     * @return
     */
    public DynamicSkuPrices getPriceData();

}
