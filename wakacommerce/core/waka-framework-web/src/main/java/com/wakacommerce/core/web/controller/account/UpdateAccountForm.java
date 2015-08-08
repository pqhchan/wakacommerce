package com.wakacommerce.core.web.controller.account;

import java.io.Serializable;

public class UpdateAccountForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailAddress;
    private String realName;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
