package com.wakacommerce.profile.web.controller.validator;

import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.form.RegisterCustomerForm;

import javax.annotation.Resource;

@Component("blRegisterCustomerValidator")
public class RegisterCustomerValidator implements Validator {

    private String validatePasswordExpression = "[0-9A-Za-z]{4,15}";

    @Resource(name="blCustomerService")
    private CustomerService customerService;

    public RegisterCustomerValidator() {}

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return clazz.equals(RegisterCustomerForm.class);
    }
    
    public void validate(Object obj, Errors errors) {
        validate(obj, errors, false);
    }
    
    public void validate(Object obj, Errors errors, boolean useEmailForUsername) {
        RegisterCustomerForm form = (RegisterCustomerForm) obj;

        Customer customerFromDb = customerService.readCustomerByUsername(form.getCustomer().getUsername());

        if (customerFromDb != null) {
            if (useEmailForUsername) {
                errors.rejectValue("customer.emailAddress", "emailAddress.used", null, null);
            } else {
                errors.rejectValue("customer.username", "username.used", null, null);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "passwordConfirm.required");
        
        errors.pushNestedPath("customer");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");
        errors.popNestedPath();


        if (!errors.hasErrors()) {

            if (!form.getPassword().matches(getValidatePasswordExpression())) {
                errors.rejectValue("password", "password.invalid", null, null);
            }

            if (!form.getPassword().equals(form.getPasswordConfirm())) {
                errors.rejectValue("password", "passwordConfirm.invalid", null, null);
            }

            if (!GenericValidator.isEmail(form.getCustomer().getEmailAddress())) {
                errors.rejectValue("customer.emailAddress", "emailAddress.invalid", null, null);
            }
        }
    }

    public String getValidatePasswordExpression() {
        return validatePasswordExpression;
    }

    public void setValidatePasswordExpression(String validatePasswordExpression) {
        this.validatePasswordExpression = validatePasswordExpression;
    }        
}
