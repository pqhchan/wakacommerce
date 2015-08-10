package com.wakacommerce.core.catalog.service;

import java.util.Date;
import java.util.List;

import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuFee;
import com.wakacommerce.core.catalog.service.type.ProductType;
import com.wakacommerce.core.search.domain.SearchCriteria;

public interface CatalogService {

    public Product saveProduct(Product product);

    public Product findProductById(Long productId);
    
    public List<Product> findProductsByName(String searchName);

    public List<Product> findProductsByName(String searchName, int limit, int offset);

    public List<Product> findActiveProductsByCategory(Category category);

    @Deprecated
    public List<Product> findActiveProductsByCategory(Category category, Date currentDate);

    public List<Product> findFilteredActiveProductsByCategory(Category category, SearchCriteria searchCriteria);

    public List<Product> findFilteredActiveProductsByCategory(Category category, Date currentDate, SearchCriteria searchCriteria);

    public List<Product> findFilteredActiveProductsByQuery(String query, SearchCriteria searchCriteria);

    public List<Product> findFilteredActiveProductsByQuery(String query, Date currentDate, SearchCriteria searchCriteria);

    public List<Product> findActiveProductsByCategory(Category category, int limit, int offset);

    @Deprecated
    public List<Product> findActiveProductsByCategory(Category category, Date currentDate, int limit, int offset);

    public List<ProductBundle> findAutomaticProductBundles();


    public Category saveCategory(Category category);
    
    public void removeCategory(Category category);

    public void removeProduct(Product product);

    public void removeSku(Sku sku);

    public Category findCategoryById(Long categoryId);

    @Deprecated
    public Category findCategoryByName(String categoryName);

    public List<Category> findCategoriesByName(String categoryName);

    public List<Category> findCategoriesByName(String categoryName, int limit, int offset);

    public List<Category> findAllCategories();

    public List<Category> findAllCategories(int limit, int offset);

    public List<Product> findAllProducts();

    public List<Product> findAllProducts(int limit, int offset);

    public List<Product> findProductsForCategory(Category category);

    public List<Product> findProductsForCategory(Category category, int limit, int offset);

    public Sku saveSku(Sku sku);
    
    public SkuFee saveSkuFee(SkuFee fee);

    public List<Sku> findAllSkus();

    public List<Sku> findSkusByIds(List<Long> ids);

    public Sku findSkuById(Long skuId);

    public Sku findSkuByUpc(String upc);

    public Category createCategory();
    
    public Sku createSku();
    
    public Product createProduct(ProductType productType);

    public List<Category> findAllParentCategories();
    
    public List<Category> findAllSubCategories(Category category);

    public List<Category> findAllSubCategories(Category category, int limit, int offset);

    public List<Category> findActiveSubCategoriesByCategory(Category category);

    public List<Category> findActiveSubCategoriesByCategory(Category category, int limit, int offset);
    
    public List<ProductOption> readAllProductOptions();
    
    public ProductOption saveProductOption(ProductOption option);
    
    public ProductOption findProductOptionById(Long productOptionId);
    
    public ProductOptionValue findProductOptionValueById(Long productOptionValueId);

    public Category findCategoryByURI(String uri);
    
    public Product findProductByURI(String uri);
    
    public Sku findSkuByURI(String uri);

}
