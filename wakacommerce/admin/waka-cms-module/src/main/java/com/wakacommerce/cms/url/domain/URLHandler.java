package com.wakacommerce.cms.url.domain;

import com.wakacommerce.cms.url.type.URLRedirectType;
import com.wakacommerce.common.copy.MultiTenantCloneable;

import java.io.Serializable;

public interface URLHandler extends Serializable, MultiTenantCloneable<URLHandler> {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract String getIncomingURL();

    public abstract void setIncomingURL(String incomingURL);

    public abstract String getNewURL();

    public abstract void setNewURL(String newURL);

    public abstract URLRedirectType getUrlRedirectType();

    public void setUrlRedirectType(URLRedirectType redirectType);

}
