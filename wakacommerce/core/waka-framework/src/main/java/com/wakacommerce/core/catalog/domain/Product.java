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
 * Product是任何可以被销售的项目的抽象（例如：帽子），但是真正被卖或被加到购物车里的是{@link Sku}
 * 而不是Product.
 * 
 * @see {@link ProductImpl}, {@link Sku}, {@link Category}
 * @author hui
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

    public List<Sku> getAdditionalSkus();
    public void setAdditionalSkus(List<Sku> skus);
    public List<Sku> getAllSkus();
    
    public Map<String, Media> getMedia();
    public void setMedia(Map<String, Media> media);

    /**
     * Convenience method for returning all of the media associated with this Product by adding
     * all the media in {@link #getDefaultSku()} as well as all the media in the Skus represented by
     * {@link #getAdditionalSkus()}
     * 
     * @return all of the Media for all of the Skus for this Product
     */
    public Map<String, Media> getAllSkuMedia();

    public Category getCategory();
    public void setCategory(Category category);
     
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
    public Weight getWeight();
    public void setWeight(Weight weight);
    public List<RelatedProduct> getCrossSaleProducts();
    public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);
    public List<RelatedProduct> getUpSaleProducts();
    public void setUpSaleProducts(List<RelatedProduct> upSaleProducts);
    public String getDimensionString();
    
    public boolean isFeaturedProduct();
    public void setFeaturedProduct(boolean isFeaturedProduct);

    public String getPromoMessage();
    public void setPromoMessage(String promoMessage);

    public List<ProductOptionXref> getProductOptionXrefs();
    public void setProductOptionXrefs(List<ProductOptionXref> productOptions);

    /**
     * Returns a Map of product option values, keyed by the product option name. 
     * E.g. "color":["red","green","black"] 
     * @return
     */
    public Map<String, Set<String>> getProductOptionValuesMap();

    /**
     * A product can have a designated URL.   When set, the ProductHandlerMapping will check for this
     * URL and forward this user to the {@link #getDisplayTemplate()}. 
     * 
     * Alternatively, most sites will rely on the {@link Product#getGeneratedUrl()} to define the
     * url for a product page. 
     * 
     * @see com.wakacommerce.core.web.catalog.ProductHandlerMapping
     * @return
     */
    public String getUrl();
    public void setUrl(String url);

    /**
     * @return the flag for whether or not the URL should not be generated in the admin
     */
    public Boolean getOverrideGeneratedUrl();
    public void setOverrideGeneratedUrl(Boolean overrideGeneratedUrl);
    
    /**
     * Sets a url-fragment.  By default, the system will attempt to create a unique url-fragment for 
     * this product by taking the {@link Product.getName()} and removing special characters and replacing
     * dashes with spaces.
     */ 
    public String getUrlKey();

    /**
     * Sets a url-fragment to be used with this product.  By default, the system will attempt to create a 
     * unique url-fragment for this product by taking the {@link Product.getName()} and removing special characters and replacing
     * dashes with spaces.
     */
    public void setUrlKey(String url);

    /**
     * Returns the name of a display template that is used to render this product.   Most implementations have a default
     * template for all products.    This allows for the user to define a specific template to be used by this product.
     * 
     * @return
     */
    public String getDisplayTemplate();

    /**
     * Sets the name of a display template that is used to render this product.   Most implementations have a default
     * template for all products.    This allows for the user to define a specific template to be used by this product.
     * @param displayTemplate
     */
    public void setDisplayTemplate(String displayTemplate);
    
    /**
     * Generates a URL that can be used to access the product.  
     * Builds the url by combining the url of the default category with the getUrlKey() of this product.
     */
    public String getGeneratedUrl();
    
    /** 
     * Returns a list of the cross sale products for this product as well
     * all cross sale products in all parent categories of this product.
     * 
     * @return the cumulative cross sale products
     */
    public List<RelatedProduct> getCumulativeCrossSaleProducts();
    
    /** 
     * Returns a list of the upsale products for this product as well as
     * all upsale products in all parent categories of this product.
     * 
     * @return the cumulative upsale products
     */
    public List<RelatedProduct> getCumulativeUpSaleProducts();

    /**
     * Removes any currently stored dynamic pricing
     */
    public void clearDynamicPrices();

    /**
     * Retrieve all the xref entities linking this product to parent categories
     */
    public List<CategoryProductXref> getAllParentCategoryXrefs();

    /**
     * Set all the xref entities linking this product to parent categories
     */
    public void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategories);

}
