package com.wakacommerce.common.email.domain;

import java.io.Serializable;
import java.util.Date;

public interface EmailTrackingOpens extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract Date getDateOpened();

    public abstract void setDateOpened(Date dateOpened);

    public abstract String getUserAgent();

    public abstract void setUserAgent(String userAgent);

    public abstract EmailTracking getEmailTracking();

    public abstract void setEmailTracking(EmailTracking emailTracking);

}
