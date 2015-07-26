
package com.wakacommerce.core.web.checkout.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.common.web.validator.BroadleafCommonAddressValidator;
import com.wakacommerce.core.web.checkout.model.BillingInfoForm;

@Component("blBillingInfoFormValidator")
public class BillingInfoFormValidator extends BroadleafCommonAddressValidator implements Validator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(BillingInfoForm.class);
    }

    public void validate(Object obj, Errors errors) {
        BillingInfoForm billingInfoForm = (BillingInfoForm) obj;
        super.validate(BroadleafFormType.BILLING_FORM, billingInfoForm.getAddress(), errors);
    }
}
