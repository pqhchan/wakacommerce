
package com.wakacommerce.core.web.controller.account;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.i18n.service.ISOService;
import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.web.controller.account.validator.CustomerAddressValidator;
import com.wakacommerce.profile.core.domain.*;
import com.wakacommerce.profile.core.service.AddressService;
import com.wakacommerce.profile.core.service.CountryService;
import com.wakacommerce.profile.core.service.CustomerAddressService;
import com.wakacommerce.profile.core.service.StateService;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.List;

/**
 *
 * @ hui
 */
public class AbstractCustomerAddressController extends WakaAbstractController {

    private static final Log LOG = LogFactory.getLog(AbstractCustomerAddressController.class);

    protected static String customerAddressesView = "account/manageCustomerAddresses";
    protected static String customerAddressesRedirect = "redirect:/account/addresses";

    @Resource(name = "blCustomerAddressService")
    protected CustomerAddressService customerAddressService;

    @Resource(name = "blAddressService")
    protected AddressService addressService;

    @Resource(name = "blCountryService")
    protected CountryService countryService;

    @Resource(name = "blCustomerAddressValidator")
    protected CustomerAddressValidator customerAddressValidator;

    @Resource(name = "blStateService")
    protected StateService stateService;

    @Resource(name = "blISOService")
    protected ISOService isoService;

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
                }else {
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

    protected List<State> populateStates() {
        return stateService.findStates();
    }

    protected List<Country> populateCountries() {
        return countryService.findCountries();
    }

    protected List<CustomerAddress> populateCustomerAddresses() {
        return customerAddressService.readActiveCustomerAddressesByCustomerId(CustomerState.getCustomer().getId());
    }

    public String getCustomerAddressesView() {
        return customerAddressesView;
    }

    public String getCustomerAddressesRedirect() {
        return customerAddressesRedirect;
    }

}
