
package com.wakacommerce.core.catalog.service;

import java.util.List;

import com.wakacommerce.core.catalog.domain.PromotableProduct;
import com.wakacommerce.core.catalog.domain.RelatedProductDTO;

/**
 * Interface for finding related products.   Could be extended to support more complex
 * related product functions.    
 * 
 * 
 *
 */
public interface RelatedProductsService {   
    
    /**
     * Uses the data in the passed in DTO to return a list of relatedProducts.
     * 
     * For example, upSale, crossSale, or featured products.
     * 
     * @param relatedProductDTO
     * @return
     */
    public List<? extends PromotableProduct> findRelatedProducts(RelatedProductDTO relatedProductDTO);
}
