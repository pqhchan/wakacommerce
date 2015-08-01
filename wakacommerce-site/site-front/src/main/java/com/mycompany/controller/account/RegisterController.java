package com.mycompany.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.controller.account.WakaRegisterController;
import com.wakacommerce.profile.web.core.form.RegisterCustomerForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class RegisterController extends WakaRegisterController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm) {
        return super.register(registerCustomerForm, request, response, model);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String processRegister(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm, BindingResult errors) throws ServiceException, PricingException {
        return super.processRegister(registerCustomerForm, errors, request, response, model);
    }
    
    @ModelAttribute("registrationForm") 
    public RegisterCustomerForm initCustomerRegistrationForm() {
        return super.initCustomerRegistrationForm();        
    }
}
