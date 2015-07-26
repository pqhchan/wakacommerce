
package com.wakacommerce.common.web.resource;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


public class AbstractResourceRequestExtensionHandler extends AbstractExtensionHandler 
        implements ResourceRequestExtensionHandler {

    @Override
    public ExtensionResultStatusType getModifiedResource(String path, ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getOverrideResource(String path, ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
