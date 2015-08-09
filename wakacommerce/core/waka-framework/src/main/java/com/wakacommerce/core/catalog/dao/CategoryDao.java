package com.wakacommerce.core.catalog.dao;

import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;

/**
 *
 * @ hui
 */
public interface CategoryDao {

    @Nonnull
    public Category readCategoryById(@Nonnull Long categoryId);

    public Category readCategoryByExternalId(@Nonnull String externalId);

    @Nonnull
    @Deprecated
    public Category readCategoryByName(@Nonnull String categoryName);

    public List<Category> readAllParentCategories();

    @Nonnull
    public List<Category> readCategoriesByName(@Nonnull String categoryName);

    @Nonnull
    public List<Category> readCategoriesByName(@Nonnull String categoryName, int limit, int offset);

    @Nonnull
    public Category save(@Nonnull Category category);

    @Nonnull
    public List<Category> readAllCategories();

    @Nonnull
    public List<Category> readAllCategories(@Nonnull int limit, @Nonnull int offset);

    @Nonnull
    public List<Product> readAllProducts();

    @Nonnull
    public List<Product> readAllProducts(@Nonnull int limit, @Nonnull int offset);

    @Nonnull
    public List<Category> readAllSubCategories(@Nonnull Category category);

    @Nonnull
    public List<Category> readAllSubCategories(@Nonnull Category category, @Nonnull int limit, @Nonnull int offset);

    public void delete(@Nonnull Category category);

    @Nonnull
    public Category create();

    @Nonnull
    public List<Category> readActiveSubCategoriesByCategory(Category category);

    @Nonnull
    public List<Category> readActiveSubCategoriesByCategory(@Nonnull Category category, @Nonnull int limit, @Nonnull int offset);

    public Category findCategoryByURI(String uri);

    public Long getCurrentDateResolution();

    public void setCurrentDateResolution(Long currentDateResolution);

}
