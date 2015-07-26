
package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;

import java.util.List;


/**
 * Extension handler for {@link com.wakacommerce.admin.server.service.AdminCatalogService}
 *
 *Jeff Fischer
 */
public interface AdminCatalogServiceExtensionHandler extends ExtensionHandler {

    public static final int DEFAULT_PRIORITY = 1000;

    /**
     * Customize the persistence of generated sku permutations based on product options.
     *
     * @param product
     * @param permutationsToGenerate
     * @param erh
     * @return
     */
    ExtensionResultStatusType persistSkuPermutation(Product product, List<List<ProductOptionValue>> permutationsToGenerate, ExtensionResultHolder<Integer> erh);

}
