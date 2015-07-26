
package com.wakacommerce.common.config.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *bpolster
 */
public abstract class AbstractSystemPropertyServiceExtensionHandler extends AbstractExtensionHandler implements
        SystemPropertyServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType resolveProperty(String propertyName, ExtensionResultHolder resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
