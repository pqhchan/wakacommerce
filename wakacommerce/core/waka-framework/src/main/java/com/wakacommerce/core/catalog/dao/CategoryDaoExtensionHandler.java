
package com.wakacommerce.core.catalog.dao;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *Jeff Fischer
 */
public interface CategoryDaoExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType findCategoryByURI(String uri, ExtensionResultHolder resultHolder);

    Long getCurrentDateResolution();

    void setCurrentDateResolution(Long currentDateResolution);

}
