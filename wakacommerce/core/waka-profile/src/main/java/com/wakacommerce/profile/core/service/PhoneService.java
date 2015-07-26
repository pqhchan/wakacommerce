
package com.wakacommerce.profile.core.service;

import com.wakacommerce.profile.core.domain.Phone;

public interface PhoneService {

    public Phone savePhone(Phone phone);

    public Phone readPhoneById(Long phoneId);

    public Phone create();
}
