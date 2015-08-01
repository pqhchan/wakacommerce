  
package com.wakacommerce.common.file.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Extension manager for {@link BroadleafFileService}
 *
 * 
 */
@Component("blBroadleafFileServiceExtensionManager")
public class BroadleafFileServiceExtensionManager extends ExtensionManager<BroadleafFileServiceExtensionHandler> {

    public BroadleafFileServiceExtensionManager() {
        super(BroadleafFileServiceExtensionHandler.class);
    }

}
