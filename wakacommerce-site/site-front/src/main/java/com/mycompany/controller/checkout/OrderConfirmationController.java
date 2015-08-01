package com.mycompany.controller.checkout;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wakacommerce.core.web.controller.checkout.BroadleafOrderConfirmationController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderConfirmationController extends BroadleafOrderConfirmationController {
    
    @Override
    @RequestMapping(value = "/confirmation/{orderNumber}", method = RequestMethod.GET)
    public String displayOrderConfirmationByOrderNumber(@PathVariable("orderNumber") String orderNumber, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        return super.displayOrderConfirmationByOrderNumber(orderNumber, model, request, response);
    }

}
