
package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.ModuleConfigurationService;
import com.wakacommerce.common.config.service.type.ModuleConfigurationType;
import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.profile.core.dao.AddressDao;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.service.exception.AddressVerificationException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service("blAddressService")
public class AddressServiceImpl implements AddressService {

    protected boolean mustValidateAddresses = false;

    @Resource(name="blAddressDao")
    protected AddressDao addressDao;

    @Resource(name = "blModuleConfigurationService")
    protected ModuleConfigurationService moduleConfigService;

    @Resource(name = "blAddressVerificationProviders")
    protected List<AddressVerificationProvider> providers;

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public Address saveAddress(Address address) {
        return addressDao.save(address);
    }

    @Override
    public Address readAddressById(Long addressId) {
        return addressDao.readAddressById(addressId);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public Address create() {
        return addressDao.create();
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void delete(Address address) {
        addressDao.delete(address);
    }

    @Override
    public List<Address> verifyAddress(Address address) throws AddressVerificationException {
        if (address.getStandardized() != null && Boolean.TRUE.equals(address.getStandardized())) {
            //If this address is already standardized, don't waste a call.
            ArrayList<Address> out = new ArrayList<Address>();
            out.add(address);
            return out;
        }

        if (providers != null && !providers.isEmpty()) {

            List<ModuleConfiguration> moduleConfigs = moduleConfigService.findActiveConfigurationsByType(ModuleConfigurationType.ADDRESS_VERIFICATION);

            if (moduleConfigs != null && !moduleConfigs.isEmpty()) {
                //Try to find a default configuration
                ModuleConfiguration config = null;
                for (ModuleConfiguration configuration : moduleConfigs) {
                    if (configuration.getIsDefault()) {
                        config = configuration;
                        break;
                    }
                }

                if (config == null) {
                    //if there wasn't a default one, use the first active one...
                    config = moduleConfigs.get(0);
                }

                for (AddressVerificationProvider provider : providers) {
                    if (provider.canRespond(config)) {
                        return provider.validateAddress(address, config);
                    }
                }
            }
        }
        if (mustValidateAddresses) {
            throw new AddressVerificationException("No providers were configured to handle address validation");
        }
        ArrayList<Address> out = new ArrayList<Address>();
        out.add(address);
        return out;
    }

    /**
     * Default is false. If set to true, the verifyAddress method will throw an exception if there are no providers to handle the request.
     * @param mustValidateAddresses
     */
    public void setMustValidateAddresses(boolean mustValidateAddresses) {
        this.mustValidateAddresses = mustValidateAddresses;
    }
}
