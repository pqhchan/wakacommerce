
package com.wakacommerce.profile.web.core.security;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.profile.core.domain.Customer;


public class DefaultCustomerMergeExtensionHandler extends AbstractExtensionHandler implements CustomerMergeExtensionHandler {

    @Override
    public ExtensionResultStatusType merge(ExtensionResultHolder<Customer> customerHolder, WebRequest request, Customer anonymousCustomer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
