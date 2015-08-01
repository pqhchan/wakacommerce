package com.wakacommerce.profile.web.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wakacommerce.profile.core.domain.CustomerPhone;
import com.wakacommerce.profile.core.domain.Phone;
import com.wakacommerce.profile.core.service.CustomerPhoneService;

import javax.annotation.Resource;
import java.util.List;

@Component("blCustomerPhoneValidator")
public class CustomerPhoneValidator implements Validator {

    @Resource(name="blCustomerPhoneService")
    private final CustomerPhoneService customerPhoneService;

    public CustomerPhoneValidator(){
        this.customerPhoneService = null;
    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return clazz.equals(Phone.class);
    }

    public void validate(Object obj, Errors errors) {
        //use regular phone
        CustomerPhone cPhone = (CustomerPhone) obj;

        if (!errors.hasErrors()) {
            //check for duplicate phone number
            List<CustomerPhone> phones = customerPhoneService.readAllCustomerPhonesByCustomerId(cPhone.getCustomer().getId());

            String phoneNum = cPhone.getPhone().getPhoneNumber();
            String phoneName = cPhone.getPhoneName();

            Long phoneId = cPhone.getPhone().getId();
            Long customerPhoneId = cPhone.getId();

            boolean foundPhoneIdForUpdate = false;
            boolean foundCustomerPhoneIdForUpdate = false;

            for (CustomerPhone existingPhone : phones) {
                //validate that the phoneId passed for an editPhone scenario exists for this user
                if(phoneId != null && !foundPhoneIdForUpdate){
                    if(existingPhone.getPhone().getId().equals(phoneId)){
                        foundPhoneIdForUpdate = true;
                    }
                }

                //validate that the customerPhoneId passed for an editPhone scenario exists for this user
                if(customerPhoneId != null && !foundCustomerPhoneIdForUpdate){
                    if(existingPhone.getId().equals(customerPhoneId)){
                        foundCustomerPhoneIdForUpdate = true;
                    }
                }

                if(existingPhone.getId().equals(cPhone.getId())){
                    continue;
                }

                if(phoneNum.equals(existingPhone.getPhone().getPhoneNumber())){
                    errors.pushNestedPath("phone");
                    errors.rejectValue("phoneNumber", "phoneNumber.duplicate", null);
                    errors.popNestedPath();
                }

                if(phoneName.equalsIgnoreCase(existingPhone.getPhoneName())){
                    errors.rejectValue("phoneName", "phoneName.duplicate", null);
                }
            }

            if(phoneId != null && !foundPhoneIdForUpdate){
                errors.pushNestedPath("phone");
                errors.rejectValue("id", "phone.invalid_id", null);
                errors.popNestedPath();
            }

            if(customerPhoneId != null && !foundCustomerPhoneIdForUpdate){
                errors.rejectValue("id", "phone.invalid_id", null);
            }
        }
    }
}
