package com.mycompany.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.web.controller.account.WakaUpdateAccountController;
import com.wakacommerce.core.web.controller.account.UpdateAccountForm;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class UpdateAccountController extends WakaUpdateAccountController {

    @RequestMapping(method = RequestMethod.GET)
    public String viewUpdateAccount(HttpServletRequest request, Model model, @ModelAttribute("updateAccountForm") UpdateAccountForm form) {
        return super.viewUpdateAccount(request, model, form);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processUpdateAccount(HttpServletRequest request, Model model, @ModelAttribute("updateAccountForm") UpdateAccountForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
        return super.processUpdateAccount(request, model, form, result, redirectAttributes);
    }


}
