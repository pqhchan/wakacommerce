
package com.wakacommerce.core.web.controller.account.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component("blUSCustomerAddressValidator")
public class USCustomerAddressValidator extends CustomerAddressValidator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(CustomerAddressValidator.class);
    }

    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.stateProvinceRegion", "state.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "postalCode.required");
    }

}
