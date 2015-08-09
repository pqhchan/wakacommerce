
package com.wakacommerce.profile.web.core.security;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.profile.core.domain.Customer;

public interface CustomerMergeExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType merge(ExtensionResultHolder<Customer> customerHolder, WebRequest request, Customer anonymousCustomer);

}
