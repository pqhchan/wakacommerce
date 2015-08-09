
package com.wakacommerce.common.web.resource;

import org.springframework.core.io.Resource;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface ResourceRequestExtensionHandler extends ExtensionHandler {
    
    public static final String RESOURCE_ATTR = "RESOURCE_ATTR";

    public ExtensionResultStatusType getModifiedResource(String path, ExtensionResultHolder erh);

    public ExtensionResultStatusType getOverrideResource(String path, ExtensionResultHolder erh);

}
