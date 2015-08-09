
package com.wakacommerce.core.web.controller.account;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.ServletWebRequest;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.service.GenericResponse;
import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.core.service.validator.ResetPasswordValidator;
import com.wakacommerce.profile.web.core.service.login.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafLoginController extends WakaAbstractController {
    
    @Resource(name="blCustomerService")
    protected CustomerService customerService;
    
    @Resource(name="blResetPasswordValidator")
    protected ResetPasswordValidator resetPasswordValidator;
    
    @Resource(name="blLoginService")
    protected LoginService loginService;
    
    protected static String loginView = "authentication/login";
    protected static String forgotPasswordView = "authentication/forgotPassword";
    protected static String forgotUsernameView = "authentication/forgotUsername";   
    protected static String forgotPasswordSuccessView = "authentication/forgotPasswordSuccess";
    protected static String resetPasswordView = "authentication/resetPassword";
    protected static String resetPasswordErrorView = "authentication/resetPasswordError";
    protected static String resetPasswordSuccessView = "redirect:/";
    protected static String resetPasswordFormView = "authentication/resetPasswordForm";

    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(request.getParameter("successUrl"))) {
            model.addAttribute("successUrl", request.getParameter("successUrl"));
        }
        return getLoginView();
    }

    public String forgotPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        return getForgotPasswordView();
    }

    public String processForgotPassword(String username, HttpServletRequest request, Model model) {
        GenericResponse errorResponse = customerService.sendForgotPasswordNotification(username, getResetPasswordUrl(request));
        if (errorResponse.getHasErrors()) {
             String errorCode = errorResponse.getErrorCodesList().get(0);
             model.addAttribute("errorCode", errorCode);             
             return getForgotPasswordView();
        } else {
            if (BLCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
                request.getSession(true).setAttribute("forgot_password_username", username);
            }
            return getForgotPasswordSuccessView();
        }
    }

    public String forgotUsername(HttpServletRequest request, HttpServletResponse response, Model model) {
        return getForgotUsernameView();
    }

    public String processForgotUsername(String email, HttpServletRequest request, HttpServletResponse response, Model model) {
        GenericResponse errorResponse = customerService.sendForgotUsernameNotification(email);
        if (errorResponse.getHasErrors()) {
            String errorCode = errorResponse.getErrorCodesList().get(0);
            request.setAttribute("errorCode", errorCode);
            return getForgotUsernameView();
        } else {
            return buildRedirectToLoginWithMessage("usernameSent");
        }
     }

    public String resetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        ResetPasswordForm resetPasswordForm = initResetPasswordForm(request);
        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return getResetPasswordView();
    }

    public String processResetPassword(ResetPasswordForm resetPasswordForm, HttpServletRequest request, HttpServletResponse response, Model model, BindingResult errors) throws ServiceException {
        resetPasswordValidator.validate(resetPasswordForm.getUsername(), resetPasswordForm.getPassword(), resetPasswordForm.getPasswordConfirm(), errors);
        if (errors.hasErrors()) {
            return getResetPasswordView();
        }
        
        GenericResponse errorResponse = customerService.resetPasswordUsingToken(
                resetPasswordForm.getUsername(), 
                resetPasswordForm.getToken(), 
                resetPasswordForm.getPassword(),
                resetPasswordForm.getPasswordConfirm());
        if (errorResponse.getHasErrors()) {
            String errorCode = errorResponse.getErrorCodesList().get(0);
            request.setAttribute("errorCode", errorCode);
            return getResetPasswordView();
        } else {            
            // The reset password was successful, so log this customer in.          
            loginService.loginCustomer(resetPasswordForm.getUsername(), resetPasswordForm.getPassword());

            return getResetPasswordSuccessView();
        }
     }

    protected String buildRedirectToLoginWithMessage(String message) {
        StringBuffer url = new StringBuffer("redirect:").append(getLoginView()).append("?messageCode=").append(message);
        return url.toString();
    }

    public ResetPasswordForm initResetPasswordForm(HttpServletRequest request) {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        String username = null;
        if (BLCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
            username = (String) request.getSession(true).getAttribute("forgot_password_username");
        }
        String token = request.getParameter("token");
        resetPasswordForm.setToken(token);
        resetPasswordForm.setUsername(username);
        return resetPasswordForm;
    }

    public String getLoginView() {
        return loginView;
    }

    public String getForgotUsernameView() {
        return forgotUsernameView;
    }

    public String getForgotPasswordView() {
        return forgotPasswordView;
    }

    public String getResetPasswordView() {
        return resetPasswordView;
    }

    public String getForgotPasswordSuccessView() {
        return forgotPasswordSuccessView;
    }

    public String getResetPasswordFormView() {
        return resetPasswordFormView;
    }
    
    public String getResetPasswordScheme(HttpServletRequest request) {
        return request.getScheme();
    }
    
    public String getResetPasswordPort(HttpServletRequest request, String scheme) {
        if ("http".equalsIgnoreCase(scheme) && request.getServerPort() != 80) {
            return ":" + request.getServerPort();
        } else if ("https".equalsIgnoreCase(scheme) && request.getServerPort() != 443) {
            return ":" + request.getServerPort();
        }
        return "";  // no port required
    }
    
    public String getResetPasswordUrl(HttpServletRequest request) {     
        String url = request.getScheme() + "://" + request.getServerName() + getResetPasswordPort(request, request.getScheme() + "/");
        
        if (request.getContextPath() != null && ! "".equals(request.getContextPath())) {
            url = url + request.getContextPath() + getResetPasswordView();
        } else {
            url = url + getResetPasswordView();
        }
        return url;
    }

    public String getResetPasswordErrorView() {
        return resetPasswordErrorView;
    }

    public String getResetPasswordSuccessView() {
        return resetPasswordSuccessView;
    }

}
