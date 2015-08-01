package com.mycompany.controller.checkout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.web.checkout.model.BillingInfoForm;
import com.wakacommerce.core.web.checkout.model.GiftCardInfoForm;
import com.wakacommerce.core.web.checkout.model.OrderInfoForm;
import com.wakacommerce.core.web.checkout.model.ShippingInfoForm;
import com.wakacommerce.core.web.controller.checkout.AbstractCheckoutController;
import com.wakacommerce.core.web.order.CartState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a stub implementation to handle gift card processing.
 * Please contact us to learn more about our AccountCredit module that handles both Gift Cards and Customer Credit.
 * http://www.broadleafcommerce.com/contact
 *
 * This should NOT be used in production, and is meant solely for demonstration purposes only.
 */
@Controller("blNullPaymentGatewayGiftCardController")
@RequestMapping("/" + NullGiftCardController.GATEWAY_CONTEXT_KEY)
public class NullGiftCardController extends AbstractCheckoutController {

    protected static final Log LOG = LogFactory.getLog(NullGiftCardController.class);
    protected static final String GATEWAY_CONTEXT_KEY = "null-giftcard";

    @RequestMapping(value="/apply", method = RequestMethod.POST)
    public String applyGiftCard(HttpServletRequest request, HttpServletResponse response, Model model,
                                @ModelAttribute("orderInfoForm") OrderInfoForm orderInfoForm,
                                @ModelAttribute("shippingInfoForm") ShippingInfoForm shippingForm,
                                @ModelAttribute("billingInfoForm") BillingInfoForm billingForm,
                                @ModelAttribute("giftCardInfoForm") GiftCardInfoForm giftCardInfoForm,
                                BindingResult result){
        Order cart = CartState.getCart();

        giftCardInfoFormValidator.validate(giftCardInfoForm, result);
        if (!result.hasErrors()) {
            result.reject("giftCardNumber", "The Gift Card module is not enabled. Please contact us for more information about our AccountCredit Module (http://www.broadleafcommerce.com/contact)");
        }

        if (!(cart instanceof NullOrderImpl)) {
            model.addAttribute("orderMultishipOptions",
                    orderMultishipOptionService.getOrGenerateOrderMultishipOptions(cart));
            model.addAttribute("paymentRequestDTO",
                    dtoTranslationService.translateOrder(cart));
        }

        populateModelWithReferenceData(request, model);

        return getCheckoutView();
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
    }

}
