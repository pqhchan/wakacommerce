package com.mycompany.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wakacommerce.common.web.controller.WakaRedirectController;
import com.wakacommerce.common.web.security.BroadleafAuthenticationSuccessRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Controller
public class RedirectController extends WakaRedirectController {
    
    @RequestMapping("/redirect")
    public String redirect(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.redirect(request, response, model);
    }
}
