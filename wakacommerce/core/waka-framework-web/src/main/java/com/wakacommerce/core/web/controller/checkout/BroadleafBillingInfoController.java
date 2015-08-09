

package com.wakacommerce.core.web.controller.checkout;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.checkout.model.BillingInfoForm;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.Phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafBillingInfoController extends AbstractCheckoutController {

    public String saveBillingAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                 BillingInfoForm billingForm, BindingResult result) throws PricingException, ServiceException {
        Order cart = CartState.getCart();

        if (billingForm.isUseShippingAddress()){
            copyShippingAddressToBillingAddress(cart, billingForm);
        }

        billingInfoFormValidator.validate(billingForm, result);
        if (result.hasErrors()) {
            return getCheckoutView();
        }

        if ((billingForm.getAddress().getPhonePrimary() != null) &&
                (StringUtils.isEmpty(billingForm.getAddress().getPhonePrimary().getPhoneNumber()))) {
            billingForm.getAddress().setPhonePrimary(null);
        }
        if ((billingForm.getAddress().getPhoneSecondary() != null) &&
                (StringUtils.isEmpty(billingForm.getAddress().getPhoneSecondary().getPhoneNumber()))) {
            billingForm.getAddress().setPhoneSecondary(null);
        }
        if ((billingForm.getAddress().getPhoneFax() != null) &&
                (StringUtils.isEmpty(billingForm.getAddress().getPhoneFax().getPhoneNumber()))) {
            billingForm.getAddress().setPhoneFax(null);
        }

        boolean found = false;
        for (OrderPayment p : cart.getPayments()) {
            if (PaymentType.CREDIT_CARD.equals(p.getType()) &&
                    p.isActive()) {
                p.setBillingAddress(billingForm.getAddress());
                found = true;
            }
        }

        if (!found) {
            // A Temporary Order Payment will be created to hold the billing address.
            // The Payment Gateway will send back any validated address and
            // the PaymentGatewayCheckoutService will persist a new payment of type CREDIT_CARD when it applies it to the Order
            OrderPayment tempOrderPayment = orderPaymentService.create();
            tempOrderPayment.setType(PaymentType.CREDIT_CARD);
            tempOrderPayment.setPaymentGatewayType(PaymentGatewayType.TEMPORARY);
            tempOrderPayment.setBillingAddress(billingForm.getAddress());
            tempOrderPayment.setOrder(cart);
            cart.getPayments().add(tempOrderPayment);
        }

        orderService.save(cart, true);

        if (isAjaxRequest(request)) {
            //Add module specific model variables
            checkoutControllerExtensionManager.getProxy().addAdditionalModelVariables(model);
            return getCheckoutView();
        } else {
            return getCheckoutPageRedirect();
        }
    }

    protected void copyShippingAddressToBillingAddress(Order order, BillingInfoForm billingInfoForm) {
        if (order.getFulfillmentGroups().get(0) != null) {
            Address shipping = order.getFulfillmentGroups().get(0).getAddress();
            if (shipping != null) {
                Address billing = addressService.create();
                billing.setFullName(shipping.getFullName());
                billing.setFirstName(shipping.getFirstName());
                billing.setLastName(shipping.getLastName());
                billing.setAddressLine1(shipping.getAddressLine1());
                billing.setAddressLine2(shipping.getAddressLine2());
                billing.setCity(shipping.getCity());
                billing.setState(shipping.getState());
                billing.setIsoCountrySubdivision(shipping.getIsoCountrySubdivision());
                billing.setStateProvinceRegion(shipping.getStateProvinceRegion());
                billing.setPostalCode(shipping.getPostalCode());
                billing.setCountry(shipping.getCountry());
                billing.setIsoCountryAlpha2(shipping.getIsoCountryAlpha2());
                billing.setPrimaryPhone(shipping.getPrimaryPhone());
                billing.setSecondaryPhone(shipping.getSecondaryPhone());
                billing.setFax(shipping.getFax());
                billing.setPhonePrimary(copyPhone(shipping.getPhonePrimary()));
                billing.setPhoneSecondary(copyPhone(shipping.getPhoneSecondary()));
                billing.setPhoneFax(copyPhone(shipping.getPhoneFax()));
                billing.setEmailAddress(shipping.getEmailAddress());
                billingInfoForm.setAddress(billing);
            }
        }
    }

    protected Phone copyPhone(Phone phoneToCopy) {
        if (phoneToCopy != null) {
            Phone copy = phoneService.create();
            copy.setPhoneNumber(phoneToCopy.getPhoneNumber());
            return copy;
        }
        return null;
    }


}
