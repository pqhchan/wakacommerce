
package com.wakacommerce.core.catalog.dao;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface SkuDaoExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType findSkuByURI(String uri, ExtensionResultHolder resultHolder);

}
