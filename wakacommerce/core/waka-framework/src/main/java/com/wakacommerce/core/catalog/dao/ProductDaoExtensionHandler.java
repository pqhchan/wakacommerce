
package com.wakacommerce.core.catalog.dao;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *Jeff Fischer
 */
public interface ProductDaoExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType findProductByURI(String uri, ExtensionResultHolder resultHolder);

    Long getCurrentDateResolution();

    void setCurrentDateResolution(Long currentDateResolution);

}
