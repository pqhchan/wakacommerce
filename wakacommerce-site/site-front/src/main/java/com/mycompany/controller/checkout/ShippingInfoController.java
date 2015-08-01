package com.mycompany.controller.checkout;

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
import com.wakacommerce.core.web.checkout.model.OrderMultishipOptionForm;
import com.wakacommerce.core.web.checkout.model.ShippingInfoForm;
import com.wakacommerce.core.web.controller.checkout.BroadleafShippingInfoController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ShippingInfoController extends BroadleafShippingInfoController {

    @RequestMapping(value="/checkout/singleship", method = RequestMethod.GET)
    public String convertToSingleship(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
        return super.convertToSingleship(request, response, model);
    }

    @RequestMapping(value="/checkout/singleship", method = RequestMethod.POST)
    public String saveSingleShip(HttpServletRequest request, HttpServletResponse response, Model model,
                                 @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
                                 @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
                                 @ModelAttribute("giftCardInfoForm") GiftCardInfoForm giftCardInfoForm,
                                 @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
                                 BindingResult result)
            throws PricingException, ServiceException {
        return super.saveSingleShip(request, response, model, shippingForm, result);
    }

    @RequestMapping(value = "/checkout/multiship", method = RequestMethod.GET)
    public String showMultiship(HttpServletRequest request, HttpServletResponse response, Model model,
                                @ModelAttribute("orderMultishipOptionForm") OrderMultishipOptionForm orderMultishipOptionForm,
                                BindingResult result) throws PricingException {
        return super.showMultiship(request, response, model);
    }

    @RequestMapping(value = "/checkout/multiship", method = RequestMethod.POST)
    public String saveMultiship(HttpServletRequest request, HttpServletResponse response, Model model,
                                @ModelAttribute("orderMultishipOptionForm") OrderMultishipOptionForm orderMultishipOptionForm,
                                BindingResult result) throws PricingException, ServiceException {
        return super.saveMultiship(request, response, model, orderMultishipOptionForm, result);
    }

    @RequestMapping(value = "/checkout/add-address", method = RequestMethod.GET)
    public String showMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                          @ModelAttribute("addressForm") ShippingInfoForm addressForm, BindingResult result) {
        return super.showMultishipAddAddress(request, response, model);
    }

    @RequestMapping(value = "/checkout/add-address", method = RequestMethod.POST)
    public String saveMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                          @ModelAttribute("addressForm") ShippingInfoForm addressForm, BindingResult result) throws ServiceException {
        return super.saveMultishipAddAddress(request, response, model, addressForm, result);
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
    }

}
