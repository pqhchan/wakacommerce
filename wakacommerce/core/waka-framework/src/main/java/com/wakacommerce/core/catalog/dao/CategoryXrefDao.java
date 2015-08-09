
package com.wakacommerce.core.catalog.dao;

import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.core.catalog.domain.CategoryProductXref;
import com.wakacommerce.core.catalog.domain.CategoryXref;
import com.wakacommerce.core.catalog.domain.CategoryXrefImpl;

/**
 *
 * @ hui
 */
public interface CategoryXrefDao {

    @Nonnull
    public List<CategoryXref> readXrefsByCategoryId(@Nonnull Long categoryId);

    @Nonnull
    public List<CategoryXref> readXrefsBySubCategoryId(@Nonnull Long subCategoryId);

    @Nonnull
    public CategoryXref readXrefByIds(@Nonnull Long categoryId, @Nonnull Long subCategoryId);

    @Nonnull
    public CategoryXref save(@Nonnull CategoryXrefImpl categoryXref);

    public void delete(@Nonnull CategoryXref categoryXref);

    @Nonnull
    public CategoryProductXref save(CategoryProductXref categoryProductXref);
}
