
package com.wakacommerce.admin.server.service.extension;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductOptionValue;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Component("blDefaultAdminCatalogExtensionHandler")
public class DefaultAdminCatalogExtensionHandler extends AbstractExtensionHandler implements AdminCatalogServiceExtensionHandler {

    @Resource(name = "blAdminCatalogServiceExtensionManager")
    protected AdminCatalogServiceExtensionManager extensionManager;

    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            extensionManager.registerHandler(this);
        }
    }

    @Override
    public ExtensionResultStatusType persistSkuPermutation(Product product, List<List<ProductOptionValue>>
            permutationsToGenerate, ExtensionResultHolder<Integer> erh) {
        int numPermutationsCreated = 0;
        //For each permutation, I need them to map to a specific Sku
        for (List<ProductOptionValue> permutation : permutationsToGenerate) {
            if (permutation.isEmpty()) continue;
            Sku permutatedSku = catalogService.createSku();
            permutatedSku.setProduct(product);
            permutatedSku.setProductOptionValues(permutation);
            permutatedSku = catalogService.saveSku(permutatedSku);
            product.getAdditionalSkus().add(permutatedSku);
            numPermutationsCreated++;
        }
        if (numPermutationsCreated != 0) {
            catalogService.saveProduct(product);
        }
        erh.setResult(numPermutationsCreated);
        return ExtensionResultStatusType.HANDLED;
    }

    @Override
    public int getPriority() {
        return DEFAULT_PRIORITY;
    }
}
