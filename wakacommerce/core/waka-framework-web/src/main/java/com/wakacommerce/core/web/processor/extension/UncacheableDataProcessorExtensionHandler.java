
package com.wakacommerce.core.web.processor.extension;

import org.thymeleaf.Arguments;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;

import java.util.Set;

/**
 *Jeff Fischer
 */
public interface UncacheableDataProcessorExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType modifyProductListForInventoryCheck(Arguments arguments, Set<Product> products, Set<Sku> skus);

}
