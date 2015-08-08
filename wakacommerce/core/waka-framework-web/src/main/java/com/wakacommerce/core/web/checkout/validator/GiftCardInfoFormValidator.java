  
package com.wakacommerce.core.web.checkout.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wakacommerce.core.web.checkout.model.GiftCardInfoForm;

/**
 *   (jocanas)
 */
@Component("blGiftCardInfoFormValidator")
public class GiftCardInfoFormValidator implements Validator {

    @Override
    @SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
        return clazz.equals(GiftCardInfoForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiftCardInfoForm giftCardInfoForm = (GiftCardInfoForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giftCardNumber", "giftCardNumber.required");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giftCardEmailAddress", "giftCardEmailAddress.required");

        //if (!errors.hasErrors()) {
        //    if (!GenericValidator.isEmail(giftCardInfoForm.getGiftCardEmailAddress())) {
        //        errors.rejectValue("giftCardEmailAddress", "giftCardEmailAddress.invalid", null, null);
        //    }
        //}
    }
}

