  
package com.wakacommerce.common.web.validator;

import org.springframework.validation.Errors;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.profile.core.domain.Address;

/**
 *  
 */
public abstract class AbstractCommonAddressValidatorExtensionHandler extends AbstractExtensionHandler
        implements BroadleafCommonAddressValidatorExtensionHandler {

    @Override
    public ExtensionResultStatusType validate(BroadleafFormType formType, Address address, Errors errors) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
