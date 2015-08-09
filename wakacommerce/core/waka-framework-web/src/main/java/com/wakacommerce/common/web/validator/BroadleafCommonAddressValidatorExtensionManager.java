
package com.wakacommerce.common.web.validator;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blBroadleafCommonAddressValidatorExtensionManager")
public class BroadleafCommonAddressValidatorExtensionManager extends ExtensionManager<BroadleafCommonAddressValidatorExtensionHandler> {

    public BroadleafCommonAddressValidatorExtensionManager() {
        super(BroadleafCommonAddressValidatorExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
