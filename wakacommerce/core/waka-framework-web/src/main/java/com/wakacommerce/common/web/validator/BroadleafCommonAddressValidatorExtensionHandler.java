  
package com.wakacommerce.common.web.validator;

import org.springframework.validation.Errors;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.profile.core.domain.Address;

/**
 *  
 */
public interface BroadleafCommonAddressValidatorExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType validate(BroadleafFormType formType, Address address, Errors errors);

}