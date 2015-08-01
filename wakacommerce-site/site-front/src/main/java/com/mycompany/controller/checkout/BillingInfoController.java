package com.mycompany.controller.checkout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.checkout.model.BillingInfoForm;
import com.wakacommerce.core.web.checkout.model.GiftCardInfoForm;
import com.wakacommerce.core.web.checkout.model.OrderInfoForm;
import com.wakacommerce.core.web.checkout.model.ShippingInfoForm;
import com.wakacommerce.core.web.controller.checkout.BroadleafBillingInfoController;

@Controller
public class BillingInfoController extends BroadleafBillingInfoController {

    @RequestMapping(value="/checkout/billing", method = RequestMethod.POST)
    public String saveBillingAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                     @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
                                     @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
                                     @ModelAttribute("giftCardInfoForm") GiftCardInfoForm giftCardInfoForm,
                                     @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
                                     BindingResult result)
            throws PricingException, ServiceException {
        return super.saveBillingAddress(request, response, model, billingForm, result);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
    }

}
