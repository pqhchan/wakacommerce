
package com.wakacommerce.common.copy;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public class AbstractMultiTenantCopierExtensionHandler extends AbstractExtensionHandler 
        implements MultiTenantCopierExtensionHandler {

    @Override
    public ExtensionResultStatusType transformCopy(MultiTenantCopyContext context, Object from, Object to) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType prepareForSave(MultiTenantCopyContext context, Object from, Object to) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
