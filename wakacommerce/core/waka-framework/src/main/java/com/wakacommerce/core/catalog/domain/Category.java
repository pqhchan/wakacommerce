package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.search.domain.CategoryExcludedSearchFacet;
import com.wakacommerce.core.search.domain.CategorySearchFacet;

/**
 * @see {@link CategoryImpl}
 * 
 * @author hui
 * 
 */
public interface Category extends Serializable, MultiTenantCloneable<Category> {

	@Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public String getName();

    public void setName(@Nonnull String name);

    public Category getParentCategory();

    public void setParentCategory(Category category);

    /**
     * 该分类的显示页面，比如说，对于spring mvc，可以是{@code "redirect:"+currentCategory.getUrl();}
     */
    @Nullable
    public String getUrl();

    public void setUrl(@Nullable String url);

    public Boolean getOverrideGeneratedUrl();

    public void setOverrideGeneratedUrl(Boolean overrideGeneratedUrl);

    @Nullable
    public String getUrlKey();

    public void setUrlKey(@Nullable String urlKey);
    
    @Nullable
    public String getGeneratedUrl();

    @Nullable
    public String getDescription();

    public void setDescription(@Nullable String description);
    
    @Nullable
    public String getLongDescription();

    public void setLongDescription(@Nullable String longDescription);

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

    public Map<String, CategoryMediaXref> getCategoryMediaXref();
    public void setCategoryMediaXref(Map<String, CategoryMediaXref> categoryMediaXref);

    /**
     * 特色商品，推荐商品
     */
    @Nonnull
    public List<FeaturedProduct> getFeaturedProducts();
    public void setFeaturedProducts(@Nonnull List<FeaturedProduct> featuredProducts);

    /**
     * 交叉销售，最佳组合，推荐配件
     */
    public List<RelatedProduct> getCrossSaleProducts();
    public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts);

    /**
     * 增销商品
     */
    public List<RelatedProduct> getUpSaleProducts();
    public void setUpSaleProducts(List<RelatedProduct> upSaleProducts);

    public List<RelatedProduct> getCumulativeCrossSaleProducts();
    public List<RelatedProduct> getCumulativeUpSaleProducts();
    public List<FeaturedProduct> getCumulativeFeaturedProducts();

    /**
     * 直接和该分类关联的SearchFacets
     */
    public List<CategorySearchFacet> getSearchFacets();
    public void setSearchFacets(List<CategorySearchFacet> searchFacets);

    /**
     * 排除的SearchFacets，通常是来自于父分类，而又不想在当前分类上应用的SearchFacets
     */
    public List<CategoryExcludedSearchFacet> getExcludedSearchFacets();
    public void setExcludedSearchFacets(List<CategoryExcludedSearchFacet> excludedSearchFacets);
   
    public List<CategorySearchFacet> getCumulativeSearchFacets();
    
    public List<Category> buildCategoryHierarchy(List<Category> currentHierarchy);
    
    public List<Category> buildFullCategoryHierarchy(List<Category> currentHierarchy);

    public InventoryType getInventoryType();
    public void setInventoryType(InventoryType inventoryType);
    
    public FulfillmentType getFulfillmentType();
    public void setFulfillmentType(FulfillmentType fulfillmentType);

    public boolean hasAllChildCategories();

    @Nonnull
    public List<Long> getChildCategoryIds();
    public void setChildCategoryIds(@Nonnull List<Long> childCategoryIds);

    public boolean hasChildCategories();

    public List<CategoryXref> getAllChildCategoryXrefs();

    public List<CategoryXref> getChildCategoryXrefs();

    public void setChildCategoryXrefs(List<CategoryXref> childCategories);

    public void setAllChildCategoryXrefs(List<CategoryXref> childCategories);

    public List<CategoryXref> getAllParentCategoryXrefs();

    public void setAllParentCategoryXrefs(List<CategoryXref> allParentCategories);

    public List<CategoryProductXref> getActiveProductXrefs();

    public List<CategoryProductXref> getAllProductXrefs();

    public void setAllProductXrefs(List<CategoryProductXref> allProducts);
    
}
