package com.wakacommerce.common.email.domain;

import java.io.Serializable;
import java.util.Date;

public interface EmailTracking extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract String getEmailAddress();

    public abstract void setEmailAddress(String emailAddress);

    public abstract Date getDateSent();

    public abstract void setDateSent(Date dateSent);

    public abstract String getType();

    public abstract void setType(String type);

}
