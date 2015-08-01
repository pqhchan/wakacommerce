

package com.wakacommerce.core.web.controller.checkout;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.checkout.model.MultiShipInstructionForm;
import com.wakacommerce.core.web.checkout.model.OrderMultishipOptionForm;
import com.wakacommerce.core.web.checkout.model.ShippingInfoForm;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerAddress;
import com.wakacommerce.profile.core.domain.Phone;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * In charge of performing the various checkout operations
 *
 * 
 *  
 *  
 */
public class BroadleafShippingInfoController extends AbstractCheckoutController {

    protected static String multishipView = "checkout/multiship";
    protected static String multishipAddAddressView = "checkout/multishipAddAddressForm";
    protected static String multishipAddAddressSuccessView = "redirect:/checkout/multiship";
    protected static String multishipSuccessView = "redirect:/checkout";

    public String getMultishipView() {
        return multishipView;
    }

    public String getMultishipAddAddressView() {
        return multishipAddAddressView;
    }

    public String getMultishipSuccessView() {
        return multishipSuccessView;
    }

    public String getMultishipAddAddressSuccessView() {
        return multishipAddAddressSuccessView;
    }

    /**
     * Converts the order to single ship by collapsing all of the shippable fulfillment groups into the default (first)
     * shippable fulfillment group.  Allows modules to add module specific shipping logic.
     *
     * @param request
     * @param response
     * @param model
     * @return a redirect to /checkout
     * @throws com.wakacommerce.core.pricing.service.exception.PricingException
     */
    public String convertToSingleship(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
        Order cart = CartState.getCart();
        fulfillmentGroupService.collapseToOneShippableFulfillmentGroup(cart, true);

        //Add module specific logic
        checkoutControllerExtensionManager.getProxy().performAdditionalShippingAction();

        return getCheckoutPageRedirect();
    }

    /**
     * Processes the request to save a single shipping address.  Allows modules to add module specific shipping logic.
     *
     * Note:  the default Broadleaf implementation creates an order
     * with a single fulfillment group. In the case of shipping to multiple addresses,
     * the multiship methods should be used.
     *
     * @param request
     * @param response
     * @param model
     * @param shippingForm
     * @return the return path
     * @throws com.wakacommerce.common.exception.ServiceException
     */
    public String saveSingleShip(HttpServletRequest request, HttpServletResponse response, Model model,
                                 ShippingInfoForm shippingForm, BindingResult result) throws PricingException, ServiceException {
        Order cart = CartState.getCart();

        if (shippingForm.isUseBillingAddress()){
            copyBillingAddressToShippingAddress(cart, shippingForm);
        }

        shippingInfoFormValidator.validate(shippingForm, result);
        if (result.hasErrors()) {
            return getCheckoutView();
        }

        if ((shippingForm.getAddress().getPhonePrimary() != null) &&
                (StringUtils.isEmpty(shippingForm.getAddress().getPhonePrimary().getPhoneNumber()))) {
            shippingForm.getAddress().setPhonePrimary(null);
        }
        if ((shippingForm.getAddress().getPhoneSecondary() != null) &&
                (StringUtils.isEmpty(shippingForm.getAddress().getPhoneSecondary().getPhoneNumber()))) {
            shippingForm.getAddress().setPhoneSecondary(null);
        }
        if ((shippingForm.getAddress().getPhoneFax() != null) &&
                (StringUtils.isEmpty(shippingForm.getAddress().getPhoneFax().getPhoneNumber()))) {
            shippingForm.getAddress().setPhoneFax(null);
        }

        FulfillmentGroup shippableFulfillmentGroup = fulfillmentGroupService.getFirstShippableFulfillmentGroup(cart);
        if (shippableFulfillmentGroup != null) {
            shippableFulfillmentGroup.setAddress(shippingForm.getAddress());
            shippableFulfillmentGroup.setPersonalMessage(shippingForm.getPersonalMessage());
            shippableFulfillmentGroup.setDeliveryInstruction(shippingForm.getDeliveryMessage());
            FulfillmentOption fulfillmentOption = fulfillmentOptionService.readFulfillmentOptionById(shippingForm.getFulfillmentOptionId());
            shippableFulfillmentGroup.setFulfillmentOption(fulfillmentOption);

            cart = orderService.save(cart, true);
        }

        //Add module specific logic
        checkoutControllerExtensionManager.getProxy().performAdditionalShippingAction();

        if (isAjaxRequest(request)) {
            //Add module specific model variables
            checkoutControllerExtensionManager.getProxy().addAdditionalModelVariables(model);
            return getCheckoutView();
        } else {
            return getCheckoutPageRedirect();
        }
    }

    /**
     * This method will copy the billing address of any CREDIT CARD order payment on the order
     * to the shipping address on the ShippingInfoForm that is passed in.
     */
    protected void copyBillingAddressToShippingAddress(Order order, ShippingInfoForm shippingInfoForm) {
        if (order.getPayments() != null) {
            for (OrderPayment payment : order.getPayments()) {
                if (payment.isActive() && PaymentType.CREDIT_CARD.equals(payment.getType())) {
                    Address billing = payment.getBillingAddress();
                    if (billing != null) {
                        Address shipping = addressService.create();
                        shipping.setFullName(billing.getFullName());
                        shipping.setFirstName(billing.getFirstName());
                        shipping.setLastName(billing.getLastName());
                        shipping.setAddressLine1(billing.getAddressLine1());
                        shipping.setAddressLine2(billing.getAddressLine2());
                        shipping.setCity(billing.getCity());
                        shipping.setState(billing.getState());
                        shipping.setIsoCountrySubdivision(billing.getIsoCountrySubdivision());
                        shipping.setStateProvinceRegion(billing.getStateProvinceRegion());
                        shipping.setPostalCode(billing.getPostalCode());
                        shipping.setCountry(billing.getCountry());
                        shipping.setIsoCountryAlpha2(billing.getIsoCountryAlpha2());
                        shipping.setPrimaryPhone(billing.getPrimaryPhone());
                        shipping.setSecondaryPhone(billing.getSecondaryPhone());
                        shipping.setFax(billing.getFax());
                        shipping.setPhonePrimary(copyPhone(billing.getPhonePrimary()));
                        shipping.setPhoneSecondary(copyPhone(billing.getPhoneSecondary()));
                        shipping.setPhoneFax(copyPhone(billing.getPhoneFax()));
                        shipping.setEmailAddress(billing.getEmailAddress());
                        shippingInfoForm.setAddress(shipping);
                    }
                }
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

    /**
     * Renders the multiship page. This page is used by the user when shipping items
     * to different locations (or with different FulfillmentOptions) is desired.
     *
     * Note that the default Broadleaf implementation will require the user to input
     * an Address and FulfillmentOption for each quantity of each DiscreteOrderItem.
     *
     * @param request
     * @param response
     * @param model
     * @return the return path
     */
    public String showMultiship(HttpServletRequest request, HttpServletResponse response, Model model) {
        Customer customer = CustomerState.getCustomer();
        Order cart = CartState.getCart();
        model.addAttribute("orderMultishipOptions", orderMultishipOptionService.getOrGenerateOrderMultishipOptions(cart));
        model.addAttribute("customerAddresses", customerAddressService.readActiveCustomerAddressesByCustomerId(customer.getId()));
        model.addAttribute("fulfillmentOptions", fulfillmentOptionService.readAllFulfillmentOptions());
        return getMultishipView();
    }

    /**
     * Processes the given options for multiship. Validates that all options are
     * selected before performing any actions.  Allows modules to add module specific shipping logic.
     *
     * @see #showMultiship(HttpServletRequest, HttpServletResponse, Model)
     *
     * @param request
     * @param response
     * @param model
     * @param orderMultishipOptionForm
     * @return a redirect to the checkout page
     * @throws PricingException
     * @throws ServiceException
     */
    public String saveMultiship(HttpServletRequest request, HttpServletResponse response, Model model,
                                OrderMultishipOptionForm orderMultishipOptionForm, BindingResult result) throws PricingException, ServiceException {
        Order cart = CartState.getCart();
        orderMultishipOptionService.saveOrderMultishipOptions(cart, orderMultishipOptionForm.getOptions());
        cart = fulfillmentGroupService.matchFulfillmentGroupsToMultishipOptions(cart, true);

        //Add module specific logic
        checkoutControllerExtensionManager.getProxy().performAdditionalShippingAction();

        return getMultishipSuccessView();
    }

    /**
     * Renders the add address form during the multiship process
     *
     * @param request
     * @param response
     * @param model
     * @return the return path
     */
    public String showMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("states", stateService.findStates());
        model.addAttribute("countries", countryService.findCountries());
        model.addAttribute("countrySubdivisions", countrySubdivisionService.findSubdivisions());
        return getMultishipAddAddressView();
    }

    /**
     * Processes the requested add address from the multiship process.
     * This method will create a CustomerAddress based on the requested Address
     * and associate it with the current Customer in session.
     *
     * @param request
     * @param response
     * @param model
     * @param addressForm
     * @return the return path to the multiship page
     * @throws ServiceException
     */
    public String saveMultishipAddAddress(HttpServletRequest request, HttpServletResponse response, Model model,
                                          ShippingInfoForm addressForm, BindingResult result) throws ServiceException {
        multishipAddAddressFormValidator.validate(addressForm, result);
        if (result.hasErrors()) {
            return showMultishipAddAddress(request, response, model);
        }

        CustomerAddress customerAddress = customerAddressService.create();
        customerAddress.setAddressName(addressForm.getAddressName());
        customerAddress.setAddress(addressForm.getAddress());
        customerAddress.setCustomer(CustomerState.getCustomer());
        customerAddressService.saveCustomerAddress(customerAddress);

        //append current time to redirect to fix a problem with ajax caching in IE
        return getMultishipAddAddressSuccessView() + "?_=" + System.currentTimeMillis();
    }

    public String saveMultiShipInstruction(HttpServletRequest request, HttpServletResponse response, Model model,
                                           MultiShipInstructionForm instructionForm) throws ServiceException, PricingException {
        Order cart = CartState.getCart();
        FulfillmentGroup fulfillmentGroup = null;

        for (FulfillmentGroup tempFulfillmentGroup : cart.getFulfillmentGroups()) {
            if (tempFulfillmentGroup.getId().equals(instructionForm.getFulfillmentGroupId())) {
                fulfillmentGroup = tempFulfillmentGroup;
            }
        }
        fulfillmentGroup.setPersonalMessage(instructionForm.getPersonalMessage());
        fulfillmentGroup.setDeliveryInstruction(instructionForm.getDeliveryMessage());
        fulfillmentGroupService.save(fulfillmentGroup);

        //append current time to redirect to fix a problem with ajax caching in IE
        return getCheckoutPageRedirect()+ "?_=" + System.currentTimeMillis();
    }

}
