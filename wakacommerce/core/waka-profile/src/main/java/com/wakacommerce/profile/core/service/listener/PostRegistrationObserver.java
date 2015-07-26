
package com.wakacommerce.profile.core.service.listener;

import com.wakacommerce.profile.core.domain.Customer;

public interface PostRegistrationObserver {

    public void processRegistrationEvent(Customer customer);
}
