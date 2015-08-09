
package com.wakacommerce.core.web.controller.checkout;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.i18n.service.ISOService;
import com.wakacommerce.common.payment.service.PaymentGatewayCheckoutService;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.checkout.service.CheckoutService;
import com.wakacommerce.core.order.service.FulfillmentGroupService;
import com.wakacommerce.core.order.service.FulfillmentOptionService;
import com.wakacommerce.core.order.service.OrderMultishipOptionService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.payment.service.OrderPaymentService;
import com.wakacommerce.core.payment.service.OrderToPaymentRequestDTOService;
import com.wakacommerce.core.web.checkout.validator.*;
import com.wakacommerce.profile.core.domain.Country;
import com.wakacommerce.profile.core.domain.Phone;
import com.wakacommerce.profile.core.domain.PhoneImpl;
import com.wakacommerce.profile.core.domain.State;
import com.wakacommerce.profile.core.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;

/**
 *
 * @ hui
 */
public abstract class AbstractCheckoutController extends WakaAbstractController {

    private static final Log LOG = LogFactory.getLog(AbstractCheckoutController.class);

    protected static String cartPageRedirect = "redirect:/cart";
    protected static String checkoutView = "checkout/checkout";
    protected static String checkoutPageRedirect = "redirect:/checkout";
    protected static String baseConfirmationView = "ajaxredirect:/confirmation";

    /* Optional Service */
    @Autowired(required=false)
    @Qualifier("blPaymentGatewayCheckoutService")
    protected PaymentGatewayCheckoutService paymentGatewayCheckoutService;

    /* Services */
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderPaymentService")
    protected OrderPaymentService orderPaymentService;

    @Resource(name = "blOrderToPaymentRequestDTOService")
    protected OrderToPaymentRequestDTOService dtoTranslationService;

    @Resource(name = "blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "blFulfillmentOptionService")
    protected FulfillmentOptionService fulfillmentOptionService;

    @Resource(name = "blCheckoutService")
    protected CheckoutService checkoutService;
    
    @Resource(name = "blCustomerService")
    protected CustomerService customerService;

    @Resource(name = "blStateService")
    protected StateService stateService;

    @Resource(name = "blCountryService")
    protected CountryService countryService;

    @Resource(name = "blCountrySubdivisionService")
    protected CountrySubdivisionService countrySubdivisionService;

    @Resource(name = "blISOService")
    protected ISOService isoService;

    @Resource(name = "blCustomerAddressService")
    protected CustomerAddressService customerAddressService;

    @Resource(name = "blAddressService")
    protected AddressService addressService;

    @Resource(name = "blPhoneService")
    protected PhoneService phoneService;

    @Resource(name = "blOrderMultishipOptionService")
    protected OrderMultishipOptionService orderMultishipOptionService;

    /* Validators */
    @Resource(name = "blShippingInfoFormValidator")
    protected ShippingInfoFormValidator shippingInfoFormValidator;

    @Resource(name = "blBillingInfoFormValidator")
    protected BillingInfoFormValidator billingInfoFormValidator;

    @Resource(name = "blGiftCardInfoFormValidator")
    protected GiftCardInfoFormValidator giftCardInfoFormValidator;

    @Resource(name = "blMultishipAddAddressFormValidator")
    protected MultishipAddAddressFormValidator multishipAddAddressFormValidator;

    @Resource(name = "blOrderInfoFormValidator")
    protected OrderInfoFormValidator orderInfoFormValidator;

    /* Extension Managers */
    @Resource(name = "blCheckoutControllerExtensionManager")
    protected BroadleafCheckoutControllerExtensionManager checkoutControllerExtensionManager;

    /* Views and Redirects */
    public String getCartPageRedirect() {
        return cartPageRedirect;
    }

    public String getCheckoutView() {
        return checkoutView;
    }

    public String getCheckoutPageRedirect() {
        return checkoutPageRedirect;
    }

    public String getBaseConfirmationView() {
        return baseConfirmationView;
    }

    protected String getConfirmationView(String orderNumber) {
        return getBaseConfirmationView() + "/" + orderNumber;
    }

    protected void populateModelWithReferenceData(HttpServletRequest request, Model model) {
        //Add module specific model variables
        checkoutControllerExtensionManager.getProxy().addAdditionalModelVariables(model);
    }

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

        binder.registerCustomEditor(State.class, "address.state", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (StringUtils.isNotEmpty(text)) {
                    State state = stateService.findStateByAbbreviation(text);
                    setValue(state);
                } else {
                    setValue(null);
                }
            }
        });

        binder.registerCustomEditor(Country.class, "address.country", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (StringUtils.isNotEmpty(text)) {
                    Country country = countryService.findCountryByAbbreviation(text);
                    setValue(country);
                } else {
                    setValue(null);
                }
            }
        });

        binder.registerCustomEditor(ISOCountry.class, "address.isoCountryAlpha2", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (StringUtils.isNotEmpty(text)) {
                    ISOCountry isoCountry = isoService.findISOCountryByAlpha2Code(text);
                    setValue(isoCountry);
                } else {
                    setValue(null);
                }
            }
        });

        binder.registerCustomEditor(Phone.class, "address.phonePrimary", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                Phone phone = new PhoneImpl();
                phone.setPhoneNumber(text);
                setValue(phone);
            }

        });

        binder.registerCustomEditor(Phone.class, "address.phoneSecondary", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                Phone phone = new PhoneImpl();
                phone.setPhoneNumber(text);
                setValue(phone);
            }

        });

        binder.registerCustomEditor(Phone.class, "address.phoneFax", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                Phone phone = new PhoneImpl();
                phone.setPhoneNumber(text);
                setValue(phone);
            }

        });
    }

}
