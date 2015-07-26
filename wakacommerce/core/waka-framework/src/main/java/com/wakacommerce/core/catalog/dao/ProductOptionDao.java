
package com.wakacommerce.core.catalog.dao;

import java.util.List;

import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;

/**
 * 
 *Phillip Verheyden
 *
 */
public interface ProductOptionDao {
    
    public List<ProductOption> readAllProductOptions();
    
    public ProductOption readProductOptionById(Long id);
    
    public ProductOption saveProductOption(ProductOption option);
    
    public ProductOptionValue readProductOptionValueById(Long id);
    
}
