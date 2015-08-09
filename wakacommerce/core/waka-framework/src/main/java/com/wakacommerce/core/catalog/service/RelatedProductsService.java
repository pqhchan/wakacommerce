
package com.wakacommerce.core.catalog.service;

import java.util.List;

import com.wakacommerce.core.catalog.domain.PromotableProduct;
import com.wakacommerce.core.catalog.domain.RelatedProductDTO;

/**
 *
 * @ hui
 */
public interface RelatedProductsService {

    public List<? extends PromotableProduct> findRelatedProducts(RelatedProductDTO relatedProductDTO);
}
