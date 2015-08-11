package com.wakacommerce.profile.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @ hui
 */
public interface CustomerForgotPasswordSecurityToken extends Serializable {

    public String getToken();

    public void setToken(String token);

    public Date getCreateDate();

    public void setCreateDate(Date date);

    public Date getTokenUsedDate();

    public void setTokenUsedDate(Date date);

    public Long getCustomerId();

    public void setCustomerId(Long customerId);

    public boolean isTokenUsedFlag();

    public void setTokenUsedFlag(boolean tokenUsed);
}
