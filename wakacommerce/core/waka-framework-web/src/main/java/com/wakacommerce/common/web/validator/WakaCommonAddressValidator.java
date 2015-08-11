package com.wakacommerce.common.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.profile.core.domain.Address;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class WakaCommonAddressValidator {

    @Resource(name = "blBroadleafCommonAddressValidatorExtensionManager")
    protected WakaCommonAddressValidatorExtensionManager validatorExtensionManager;

    public boolean isValidateFullNameOnly() {
        return BLCSystemProperty.resolveBooleanSystemProperty("validator.address.fullNameOnly");
    }

    public boolean isCustomValidationEnabled() {
        return BLCSystemProperty.resolveBooleanSystemProperty("validator.custom.enabled");
    }

    public void validate(BroadleafFormType formType, Address address, Errors errors) {
        if (isCustomValidationEnabled())  {
            validatorExtensionManager.getProxy().validate(formType, address, errors);
            return;
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.addressLine1", "addressLine1.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "city.required");
        if (isValidateFullNameOnly()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.fullName", "fullName.required");
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.firstName", "firstName.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.lastName", "lastName.required");
        }

        if (address.getIsoCountryAlpha2() == null && address.getCountry() == null) {
            errors.rejectValue("address.isoCountryAlpha2", "country.required", null, null);
        }
    }

}
