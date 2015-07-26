
package com.wakacommerce.core.catalog.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Jeff Fischer
 */
@Service("blProductDaoExtensionManager")
public class ProductDaoExtensionManager extends ExtensionManager<ProductDaoExtensionHandler> {

    public ProductDaoExtensionManager() {
        super(ProductDaoExtensionHandler.class);
    }

}