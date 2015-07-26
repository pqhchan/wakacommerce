
package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.profile.core.dao.CustomerAddressDao;
import com.wakacommerce.profile.core.domain.CustomerAddress;

import java.util.List;

import javax.annotation.Resource;

@Service("blCustomerAddressService")
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Resource(name="blCustomerAddressDao")
    protected CustomerAddressDao customerAddressDao;

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
        // if parameter address is set as default, unset all other default addresses
        List<CustomerAddress> activeCustomerAddresses = readActiveCustomerAddressesByCustomerId(customerAddress.getCustomer().getId());
        if (activeCustomerAddresses != null && activeCustomerAddresses.isEmpty()) {
            customerAddress.getAddress().setDefault(true);
        } else {
            if (customerAddress.getAddress().isDefault()) {
                for (CustomerAddress activeCustomerAddress : activeCustomerAddresses) {
                    if (!activeCustomerAddress.getId().equals(customerAddress.getId()) && activeCustomerAddress.getAddress().isDefault()) {
                        activeCustomerAddress.getAddress().setDefault(false);
                        customerAddressDao.save(activeCustomerAddress);
                    }
                }
            }
        }
        return customerAddressDao.save(customerAddress);
    }

    @Override
    public List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId) {
        return customerAddressDao.readActiveCustomerAddressesByCustomerId(customerId);
    }

    @Override
    public CustomerAddress readCustomerAddressById(Long customerAddressId) {
        return customerAddressDao.readCustomerAddressById(customerAddressId);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void makeCustomerAddressDefault(Long customerAddressId, Long customerId) {
        customerAddressDao.makeCustomerAddressDefault(customerAddressId, customerId);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void deleteCustomerAddressById(Long customerAddressId){
        customerAddressDao.deleteCustomerAddressById(customerAddressId);
    }

    @Override
    public CustomerAddress findDefaultCustomerAddress(Long customerId) {
        return customerAddressDao.findDefaultCustomerAddress(customerId);
    }

    @Override
    public CustomerAddress create() {
        return customerAddressDao.create();
    }
}
