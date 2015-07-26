
package com.wakacommerce.core.web.controller.account.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.common.web.validator.BroadleafCommonAddressValidator;
import com.wakacommerce.core.web.controller.account.CustomerAddressForm;

@Component("blCustomerAddressValidator")
public class CustomerAddressValidator extends BroadleafCommonAddressValidator implements Validator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(CustomerAddressValidator.class);
    }

    public void validate(Object obj, Errors errors) {
        CustomerAddressForm form = (CustomerAddressForm) obj;
        super.validate(BroadleafFormType.CUSTOMER_ADDRESS_FORM, form.getAddress(), errors);
    }
}

