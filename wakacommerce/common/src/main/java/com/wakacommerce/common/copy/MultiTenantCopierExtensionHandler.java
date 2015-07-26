
package com.wakacommerce.common.copy;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


public interface MultiTenantCopierExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType transformCopy(MultiTenantCopyContext context, Object from, Object to);
    
    public ExtensionResultStatusType prepareForSave(MultiTenantCopyContext context, Object from, Object to);

}
