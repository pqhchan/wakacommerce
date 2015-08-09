
package com.wakacommerce.core.catalog.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.service.type.ProductType;
import com.wakacommerce.core.search.domain.SearchCriteria;

/**
 *
 * @ hui
 */
public interface ProductDao {

    @Nonnull
    public Product readProductById(@Nonnull Long productId);
    
    public Product readProductByExternalId(String externalId);

    public List<Product> readProductsByIds(@Nonnull List<Long> productIds);

    @Nonnull
    public Product save(@Nonnull Product product);

    @Nonnull
    public List<Product> readProductsByName(@Nonnull String searchName);

    @Nonnull
    public List<Product> readProductsByName(@Nonnull String searchName, @Nonnull int limit, @Nonnull int offset);

    public List<Product> readActiveProductsByCategory(@Nonnull Long categoryId);

    public List<Product> readActiveProductsByCategory(@Nonnull Long categoryId, @Nonnull int limit, @Nonnull int offset);

    @Nonnull
    public List<Product> readFilteredActiveProductsByCategory(Long categoryId, SearchCriteria searchCriteria);

    @Nonnull
    public List<Product> readFilteredActiveProductsByQuery(String query, SearchCriteria searchCriteria);

    @Deprecated
    @Nonnull
    public List<Product> readFilteredActiveProductsByCategory(Long categoryId, Date currentDate, SearchCriteria searchCriteria);

    @Deprecated
    @Nonnull
    public List<Product> readActiveProductsByCategory(@Nonnull Long categoryId, @Nonnull Date currentDate);

    @Deprecated
    @Nonnull
    public List<Product> readFilteredActiveProductsByQuery(String query, Date currentDate, SearchCriteria searchCriteria);

    @Deprecated
    @Nonnull
    public List<Product> readActiveProductsByCategory(@Nonnull Long categoryId, @Nonnull Date currentDate, @Nonnull int limit, @Nonnull int offset);

    @Nonnull
    public List<Product> readProductsByCategory(@Nonnull Long categoryId);

    @Nonnull
    public List<Product> readProductsByCategory(@Nonnull Long categoryId, @Nonnull int limit, @Nonnull int offset);

    public void delete(@Nonnull Product product);

    public Product create(ProductType productType);

    public List<ProductBundle> readAutomaticProductBundles();

    public List<Product> findProductByURI(String key);

    public List<Product> readAllActiveProducts();

    @Deprecated
    public List<Product> readAllActiveProducts(@Nonnull Date currentDate);

    public List<Product> readAllActiveProducts(int page, int pageSize);

    @Deprecated
    public List<Product> readAllActiveProducts(int page, int pageSize, Date currentDate);

    public Long readCountAllActiveProducts();

    @Deprecated
    public Long readCountAllActiveProducts(Date currentDate);

    public Long getCurrentDateResolution();

    public void setCurrentDateResolution(Long currentDateResolution);
}
