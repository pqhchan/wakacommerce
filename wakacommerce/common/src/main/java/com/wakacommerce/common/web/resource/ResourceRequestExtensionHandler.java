
package com.wakacommerce.common.web.resource;

import org.springframework.core.io.Resource;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 * Provides extension points for dealing with requests for resources
 * 
 *bpolster, apazzolini
 */
public interface ResourceRequestExtensionHandler extends ExtensionHandler {
    
    public static final String RESOURCE_ATTR = "RESOURCE_ATTR";
    
    /**
     * Populates the RESOURCE_ATTR field in the ExtensionResultHolder map with an instance of
     * {@link Resource} if the value of the modified resource.
     * 
     * @param path
     * @param erh
     * @return the {@link ExtensionResultStatusType}
     */
    public ExtensionResultStatusType getModifiedResource(String path, ExtensionResultHolder erh);

    /**
     * Populates the RESOURCE_ATTR field in the ExtensionResultHolder map with an instance of
     * {@link Resource} if there is an override resource available for the current path.
     * 
     * @param path
     * @param erh
     * @return the {@link ExtensionResultStatusType}
     */
    public ExtensionResultStatusType getOverrideResource(String path, ExtensionResultHolder erh);

}
