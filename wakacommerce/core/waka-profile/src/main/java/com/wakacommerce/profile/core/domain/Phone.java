package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

public interface Phone extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    public String getExtension();

    public void setExtension(String extension);

    public boolean isDefault();

    public void setDefault(boolean isDefault);

    public boolean isActive();

    public void setActive(boolean isActive);
}
