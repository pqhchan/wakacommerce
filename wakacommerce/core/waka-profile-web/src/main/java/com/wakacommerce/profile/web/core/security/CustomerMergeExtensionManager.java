package com.wakacommerce.profile.web.core.security;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blCustomerMergeExtensionManager")
public class CustomerMergeExtensionManager extends ExtensionManager<CustomerMergeExtensionHandler> {

    public CustomerMergeExtensionManager() {
        super(CustomerMergeExtensionHandler.class);
    }

}
