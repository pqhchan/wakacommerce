
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

public interface PersonalMessage extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getMessageTo();

    public void setMessageTo(String messageTo);

    public String getMessageFrom();

    public void setMessageFrom(String messageFrom);

    public String getMessage();

    public void setMessage(String message);

    public String getOccasion();

    public void setOccasion(String occasion);
}
