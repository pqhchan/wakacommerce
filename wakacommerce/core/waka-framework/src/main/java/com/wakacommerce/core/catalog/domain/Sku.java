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
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.service.type.FulfillmentType;

/**
 *
 * @ hui
 */
public interface Sku extends Serializable, MultiTenantCloneable<Sku> {

    public Long getId();

    public void setId(Long id);
 
    public String getUrlKey();

    public void setUrlKey(String url);

    public String getDisplayTemplate();

    public void setDisplayTemplate(String displayTemplate);

    public Money getProductOptionValueAdjustments();

    public Money getSalePrice();

    public void setSalePrice(Money salePrice);

    public boolean hasSalePrice();

    public Money getRetailPrice();

    public void setRetailPrice(Money retailPrice);

    public boolean hasRetailPrice();

    public Money getPrice();

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public String getLongDescription();

    public void setLongDescription(String longDescription);

    public Boolean isTaxable();

    public Boolean getTaxable();

    public void setTaxable(Boolean taxable);

    public Boolean isDiscountable();

    public void setDiscountable(Boolean discountable);

    @Deprecated
    public Boolean isAvailable();

    @Deprecated
    public Boolean getAvailable();

    @Deprecated
    public void setAvailable(Boolean available);

    public Date getActiveStartDate();

    public void setActiveStartDate(Date activeStartDate);

    public Date getActiveEndDate();

    public void setActiveEndDate(Date activeEndDate);

    public Dimension getDimension();

    public void setDimension(Dimension dimension);

    public Weight getWeight();

    public void setWeight(Weight weight);

    public boolean isActive();

    @Deprecated
    public Map<String, Media> getSkuMedia();

    @Deprecated
    public void setSkuMedia(Map<String, Media> skuMedia);

    Map<String, SkuMediaXref> getSkuMediaXref();

    void setSkuMediaXref(Map<String, SkuMediaXref> skuMediaXref);

    public boolean isActive(Product product, Category category);

    public Map<String, SkuAttribute> getSkuAttributes();

    public void setSkuAttributes(Map<String, SkuAttribute> skuAttributes);

    @Deprecated
    public List<ProductOptionValue> getProductOptionValues();

    @Deprecated
    public void setProductOptionValues(List<ProductOptionValue> productOptionValues);

    Set<ProductOptionValue> getProductOptionValuesCollection();

    void setProductOptionValuesCollection(Set<ProductOptionValue> productOptionValues);

    public Set<SkuProductOptionValueXref> getProductOptionValueXrefs();

    public void setProductOptionValueXrefs(Set<SkuProductOptionValueXref> productOptionValueXrefs);

    public Product getDefaultProduct();

    public void setDefaultProduct(Product product);

    public Product getProduct();

    public void setProduct(Product product);

    public boolean isOnSale();

    @Deprecated
    public Boolean isMachineSortable();

    public Boolean getIsMachineSortable();

    @Deprecated
    public void setMachineSortable(Boolean isMachineSortable);

    public void setIsMachineSortable(Boolean isMachineSortable);

    public List<SkuFee> getFees();

    public void setFees(List<SkuFee> fees);

    public Map<FulfillmentOption, BigDecimal> getFulfillmentFlatRates();

    public void setFulfillmentFlatRates(Map<FulfillmentOption, BigDecimal> fulfillmentFlatRates);

    public List<FulfillmentOption> getExcludedFulfillmentOptions();

    public void setExcludedFulfillmentOptions(List<FulfillmentOption> excludedFulfillmentOptions);

    public InventoryType getInventoryType();

    public void setInventoryType(InventoryType inventoryType);

    public Integer getQuantityAvailable();

    public void setQuantityAvailable(Integer quantityAvailable);

    public FulfillmentType getFulfillmentType();

    public void setFulfillmentType(FulfillmentType fulfillmentType);

    public void clearDynamicPrices();

    public void setCurrency(BroadleafCurrency currency);

    public BroadleafCurrency getCurrency();

    public String getUpc();

    public void setUpc(String upc);

    public DynamicSkuPrices getPriceData();

}
