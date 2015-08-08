  
package com.wakacommerce.core.web.checkout.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.wakacommerce.core.web.checkout.model.BillingInfoForm;

@Component("blUSBillingInfoFormValidator")
public class USBillingInfoFormValidator extends BillingInfoFormValidator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(BillingInfoForm.class);
    }

    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.stateProvinceRegion", "state.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "postalCode.required");
    }

}
