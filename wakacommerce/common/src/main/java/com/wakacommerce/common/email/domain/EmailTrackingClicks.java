package com.wakacommerce.common.email.domain;

import java.io.Serializable;
import java.util.Date;

public interface EmailTrackingClicks extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract Date getDateClicked();

    public abstract void setDateClicked(Date dateClicked);

    public abstract String getDestinationUri();

    public abstract void setDestinationUri(String destinationUri);

    public abstract String getQueryString();

    public abstract void setQueryString(String queryString);

    public abstract EmailTracking getEmailTracking();

    public abstract void setEmailTracking(EmailTracking emailTracking);

    public abstract String getCustomerId();

    public abstract void setCustomerId(String customerId);

}
