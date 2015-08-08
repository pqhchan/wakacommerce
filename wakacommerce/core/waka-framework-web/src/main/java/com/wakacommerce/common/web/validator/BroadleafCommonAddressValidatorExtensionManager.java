  
package com.wakacommerce.common.web.validator;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *  
 */
@Service("blBroadleafCommonAddressValidatorExtensionManager")
public class BroadleafCommonAddressValidatorExtensionManager extends ExtensionManager<BroadleafCommonAddressValidatorExtensionHandler> {

    public BroadleafCommonAddressValidatorExtensionManager() {
        super(BroadleafCommonAddressValidatorExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
