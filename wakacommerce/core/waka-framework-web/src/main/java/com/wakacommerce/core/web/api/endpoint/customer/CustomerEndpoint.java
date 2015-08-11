package com.wakacommerce.core.web.api.endpoint.customer;

import com.wakacommerce.core.web.api.endpoint.BaseEndpoint;
import com.wakacommerce.profile.core.service.CustomerService;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public abstract class CustomerEndpoint extends BaseEndpoint {

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

}
