package com.wakacommerce.core.web.controller.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.web.controller.account.validator.UpdateAccountValidator;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class WakaUpdateAccountController extends WakaAbstractController {

    @Value("${use.email.for.site.login:true}")
    protected boolean useEmailForLogin;

    @Resource(name = "blCustomerService")
    protected CustomerService customerService;
    
    @Resource(name = "blUpdateAccountValidator")
    protected UpdateAccountValidator updateAccountValidator;

    protected String accountUpdatedMessage = "Account successfully updated";
    
    protected static String updateAccountView = "account/updateAccount";
    protected static String accountRedirectView = "redirect:/account";

    public String viewUpdateAccount(HttpServletRequest request, Model model, UpdateAccountForm form) {
        Customer customer = CustomerState.getCustomer();
        form.setEmailAddress(customer.getEmailAddress());
        form.setRealName(customer.getRealName());
        return getUpdateAccountView();
    }

    public String processUpdateAccount(HttpServletRequest request, Model model, UpdateAccountForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
        updateAccountValidator.validate(form, result);
        if (result.hasErrors()) {
            return getUpdateAccountView();
        }
        Customer customer = CustomerState.getCustomer();
        customer.setEmailAddress(form.getEmailAddress());
        customer.setRealName(form.getRealName());

        if (useEmailForLogin) {
            customer.setUsername(form.getEmailAddress());
        }

        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", getAccountUpdatedMessage());
        return getAccountRedirectView();
    }

    public String getUpdateAccountView() {
        return updateAccountView;
    }
    
    public String getAccountRedirectView() {
        return accountRedirectView;
    }
    
    public String getAccountUpdatedMessage() {
        return accountUpdatedMessage;
    }

}
