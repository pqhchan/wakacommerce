
package com.wakacommerce.profile.core.service;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.ModuleProvider;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.service.exception.AddressVerificationException;

import java.util.List;

/**
 *
 * @ hui
 */
public interface AddressVerificationProvider extends ModuleProvider {

    public List<Address> validateAddress(Address address, ModuleConfiguration config) throws AddressVerificationException;

}
