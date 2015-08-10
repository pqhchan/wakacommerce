package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.common.vendor.service.type.ContainerShapeType;
import com.wakacommerce.common.vendor.service.type.ContainerSizeType;

/**
 *
 * @ hui
 */
public interface Product extends Serializable, MultiTenantCloneable<Product> {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public String getLongDescription();

    public void setLongDescription(String longDescription);

    public Date getActiveStartDate();

    public void setActiveStartDate(Date activeStartDate);

    public Date getActiveEndDate();

    public void setActiveEndDate(Date activeEndDate);

    public boolean isActive();

    public Sku getDefaultSku();

    public void setDefaultSku(Sku defaultSku);

    public Boolean getCanSellWithoutOptions();

    public void setCanSellWithoutOptions(Boolean canSellWithoutOptions);

    @Deprecated
    public List<Sku> getSkus();

    public List<Sku> getAdditionalSkus();

    public void setAdditionalSkus(List<Sku> skus);

    public List<Sku> getAllSkus();

    public Map<String, Media> getMedia();

    public void setMedia(Map<String, Media> media);

    public Map<String, Media> getAllSkuMedia();

    public Category getCategory();

    public void setCategory(Category category);

    @Deprecated
    public Category getDefaultCategory();

    @Deprecated
    public void setDefaultCategory(Category defaultCategory);

    public String getModel();

    public void setModel(String model);

    public String getManufacturer();

    public void setManufacturer(String manufacturer);

    public Dimension getDimension();

    public void setDimension(Dimension dimension);

    public BigDecimal getWidth();

    public void setWidth(BigDecimal width);

    public BigDecimal getHeight();

    public void setHeight(BigDecimal height);

    public BigDecimal getDepth();

    public void setDepth(BigDecimal depth);

    public BigDecimal getGirth();

    public void setGirth(BigDecimal girth);

    public ContainerSizeType getSize();

    public void setSize(ContainerSizeType size);

    public ContainerShapeType getContainer();

    public void setContainer(ContainerShapeType container);

    public String getDimensionString();

    public Weight getWeight();

    public void setWeight(Weight weight);

    public List<RelatedProduct> getCrossSaleProducts();

    public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);

    public List<RelatedProduct> getUpSaleProducts();

    public void setUpSaleProducts(List<RelatedProduct> upSaleProducts);

    public boolean isFeaturedProduct();

    public void setFeaturedProduct(boolean isFeaturedProduct);

    public Map<String, ProductAttribute> getProductAttributes();

    public void setProductAttributes(Map<String, ProductAttribute> productAttributes);

    public String getPromoMessage();

    public void setPromoMessage(String promoMessage);

    @Deprecated
    public List<ProductOption> getProductOptions();

    public List<ProductOptionXref> getProductOptionXrefs();

    @Deprecated
    public void setProductOptions(List<ProductOption> productOptions);

    public void setProductOptionXrefs(List<ProductOptionXref> productOptions);

    public Map<String, Set<String>> getProductOptionValuesMap();

    public String getUrl();

    public void setUrl(String url);

    public Boolean getOverrideGeneratedUrl();

    public void setOverrideGeneratedUrl(Boolean overrideGeneratedUrl);
 
    public String getUrlKey();

    public void setUrlKey(String url);

    public String getDisplayTemplate();

    public void setDisplayTemplate(String displayTemplate);

    public String getGeneratedUrl();

    public List<RelatedProduct> getCumulativeCrossSaleProducts();

    public List<RelatedProduct> getCumulativeUpSaleProducts();

    public void clearDynamicPrices();

    public List<CategoryProductXref> getAllParentCategoryXrefs();

    public void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategories);

    @Deprecated
    public List<Category> getAllParentCategories();

    @Deprecated
    public void setAllParentCategories(List<Category> allParentCategories);

    public String getTaxCode();

    public void setTaxCode(String taxCode);
}
