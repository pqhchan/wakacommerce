
package com.wakacommerce.core.web.controller.account;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.security.util.PasswordChange;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.web.controller.account.validator.ChangePasswordValidator;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class WakaChangePasswordController extends WakaAbstractController {

    @Resource(name = "blCustomerService")
    protected CustomerService customerService;
    @Resource(name = "blChangePasswordValidator")
    protected ChangePasswordValidator changePasswordValidator;

    protected String passwordChangedMessage = "Password successfully changed";
    
    protected static String changePasswordView = "account/changePassword";
    protected static String changePasswordRedirect = "redirect:/account/password";

    public String viewChangePassword(HttpServletRequest request, Model model) {
        return getChangePasswordView();
    }

    public String processChangePassword(HttpServletRequest request, Model model, ChangePasswordForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
        PasswordChange passwordChange = new PasswordChange(CustomerState.getCustomer().getUsername());
        passwordChange.setCurrentPassword(form.getCurrentPassword());
        passwordChange.setNewPassword(form.getNewPassword());
        passwordChange.setNewPasswordConfirm(form.getNewPasswordConfirm());
        changePasswordValidator.validate(passwordChange, result);
        if (result.hasErrors()) {
            return getChangePasswordView();
        }
        customerService.changePassword(passwordChange);
        return getChangePasswordRedirect();
    }

    public String getChangePasswordView() {
        return changePasswordView;
    }
    
    public String getChangePasswordRedirect() {
        return changePasswordRedirect;
    }
    
    public String getPasswordChangedMessage() {
        return passwordChangedMessage;
    }
    
}
