
package com.wakacommerce.core.catalog.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface CatalogServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType findCategoryByURI(String uri, ExtensionResultHolder resultHolder);

    public ExtensionResultStatusType findProductByURI(String uri, ExtensionResultHolder resultHolder);

    public ExtensionResultStatusType findSkuByURI(String uri, ExtensionResultHolder resultHolder);

}
