package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;

import java.util.List;

public interface AdminCatalogServiceExtensionHandler extends ExtensionHandler {

    public static final int DEFAULT_PRIORITY = 1000;

    ExtensionResultStatusType persistSkuPermutation(Product product, List<List<ProductOptionValue>> permutationsToGenerate, ExtensionResultHolder<Integer> erh);

}
