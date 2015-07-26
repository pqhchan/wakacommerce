
package com.wakacommerce.profile.core.dao;

import com.wakacommerce.profile.core.domain.Phone;

public interface PhoneDao {

    public Phone save(Phone phone);

    public Phone readPhoneById(Long phoneId);

    public Phone create();
}
