package com.wakacommerce.profile.core.service.handler;

import com.wakacommerce.common.security.util.PasswordReset;
import com.wakacommerce.profile.core.domain.Customer;

public interface PasswordUpdatedHandler {

    public void passwordChanged(PasswordReset passwordReset, Customer customer, String newPassword);

}
