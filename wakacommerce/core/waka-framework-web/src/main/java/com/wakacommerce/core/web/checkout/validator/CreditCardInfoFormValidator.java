

package com.wakacommerce.core.web.checkout.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wakacommerce.core.web.checkout.model.CreditCardInfoForm;

/**
 *  
 */
@Component("blCreditCardInfoFormValidator")
public class CreditCardInfoFormValidator implements Validator {

    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(CreditCardInfoForm.class);
    }

    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardName", "creditCardName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNumber", "creditCardNumber.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardCvvCode", "creditCardCvvCode.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardExpMonth", "creditCardExpMonth.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardExpYear", "creditCardExpYear.required");
    }
}
