package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.search.domain.CategoryExcludedSearchFacet;
import com.wakacommerce.core.search.domain.CategorySearchFacet;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Category extends Serializable, MultiTenantCloneable<Category> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public String getName();

    public void setName(@Nonnull String name);

    @Deprecated
    @Nullable
    public Category getDefaultParentCategory();

    @Deprecated
    public void setDefaultParentCategory(@Nullable Category defaultParentCategory);

    Category getParentCategory();

    void setParentCategory(Category category);

    @Nullable
    public String getUrl();

    public void setUrl(@Nullable String url);

    public Boolean getOverrideGeneratedUrl();

    public void setOverrideGeneratedUrl(Boolean overrideGeneratedUrl);

    @Nullable
    public String getUrlKey();

    @Nullable
    public String getGeneratedUrl();

    public void setUrlKey(@Nullable String urlKey);

    @Nullable
    public String getDescription();

    public void setDescription(@Nullable String description);

    @Nullable
    public Date getActiveStartDate();

    public void setActiveStartDate(@Nullable Date activeStartDate);

    @Nullable
    public Date getActiveEndDate();

    public void setActiveEndDate(@Nullable Date activeEndDate);

    public boolean isActive();

    @Nullable
    public String getDisplayTemplate();

    public void setDisplayTemplate(@Nullable String displayTemplate);

    @Deprecated
    @Nonnull
    public Map<String,List<Long>> getChildCategoryURLMap();

    @Deprecated
    public void setChildCategoryURLMap(@Nonnull Map<String, List<Long>> childCategoryURLMap);

    @Nonnull
    @Deprecated
    public Map<String, Media> getCategoryMedia() ;

    @Deprecated
    public void setCategoryMedia(@Nonnull Map<String, Media> categoryMedia);

    public Map<String, CategoryMediaXref> getCategoryMediaXref();

    public void setCategoryMediaXref(Map<String, CategoryMediaXref> categoryMediaXref);

    @Nullable
    public String getLongDescription();

    public void setLongDescription(@Nullable String longDescription);

    @Nonnull
    public List<FeaturedProduct> getFeaturedProducts();

    public void setFeaturedProducts(@Nonnull List<FeaturedProduct> featuredProducts);

    public List<RelatedProduct> getCrossSaleProducts();

    public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);

    public List<RelatedProduct> getUpSaleProducts();

    public void setUpSaleProducts(List<RelatedProduct> upSaleProducts);

    public List<RelatedProduct> getCumulativeCrossSaleProducts();

    public List<RelatedProduct> getCumulativeUpSaleProducts();

    public List<FeaturedProduct> getCumulativeFeaturedProducts();

    public List<CategorySearchFacet> getSearchFacets();

    public void setSearchFacets(List<CategorySearchFacet> searchFacets);

    public void setExcludedSearchFacets(List<CategoryExcludedSearchFacet> excludedSearchFacets);

    public List<CategoryExcludedSearchFacet> getExcludedSearchFacets();

    public List<CategorySearchFacet> getCumulativeSearchFacets();

    
    public List<Category> buildCategoryHierarchy(List<Category> currentHierarchy);

    public List<Category> buildFullCategoryHierarchy(List<Category> currentHierarchy);

    public Map<String, CategoryAttribute> getCategoryAttributesMap();

    public void setCategoryAttributesMap(Map<String, CategoryAttribute> categoryAttributes);

    @Deprecated
    public List<CategoryAttribute> getCategoryAttributes();

    @Deprecated
    public void setCategoryAttributes(List<CategoryAttribute> categoryAttributes);

    @Deprecated
    public CategoryAttribute getCategoryAttributeByName(String name);

    @Deprecated
    public Map<String, CategoryAttribute> getMappedCategoryAttributes();

    public InventoryType getInventoryType();

    public void setInventoryType(InventoryType inventoryType);

    public FulfillmentType getFulfillmentType();

    public void setFulfillmentType(FulfillmentType fulfillmentType);

    @Nonnull
    @Deprecated
    public List<Category> getAllChildCategories();

    public boolean hasAllChildCategories();

    @Deprecated
    public void setAllChildCategories(@Nonnull List<Category> childCategories);

    @Deprecated
    @Nonnull
    public List<Category> getChildCategories();

    @Nonnull
    public List<Long> getChildCategoryIds();

    public void setChildCategoryIds(@Nonnull List<Long> childCategoryIds);

    public boolean hasChildCategories();

    @Deprecated
    public void setChildCategories(@Nonnull List<Category> childCategories);

    public List<CategoryXref> getAllChildCategoryXrefs();

    public List<CategoryXref> getChildCategoryXrefs();

    public void setChildCategoryXrefs(List<CategoryXref> childCategories);

    public void setAllChildCategoryXrefs(List<CategoryXref> childCategories);

    public List<CategoryXref> getAllParentCategoryXrefs();

    public void setAllParentCategoryXrefs(List<CategoryXref> allParentCategories);

    @Deprecated
    @Nonnull
    public List<Category> getAllParentCategories();

    @Deprecated
    public void setAllParentCategories(@Nonnull List<Category> allParentCategories);

    public List<CategoryProductXref> getActiveProductXrefs();

    public List<CategoryProductXref> getAllProductXrefs();

    public void setAllProductXrefs(List<CategoryProductXref> allProducts);

    @Deprecated
    public List<Product> getActiveProducts();

    @Deprecated
    @Nonnull
    public List<Product> getAllProducts();

    @Deprecated
    public void setAllProducts(@Nonnull List<Product> allProducts);

    public String getTaxCode();

    public void setTaxCode(String taxCode);

    public String getExternalId();

    public void setExternalId(String externalId);

}
