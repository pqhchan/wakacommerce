
package com.wakacommerce.common.web.validator;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blBroadleafCommonAddressValidatorExtensionManager")
public class WakaCommonAddressValidatorExtensionManager extends ExtensionManager<WakaCommonAddressValidatorExtensionHandler> {

    public WakaCommonAddressValidatorExtensionManager() {
        super(WakaCommonAddressValidatorExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
