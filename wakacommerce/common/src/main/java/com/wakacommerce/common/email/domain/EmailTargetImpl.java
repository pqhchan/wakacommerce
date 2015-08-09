package com.wakacommerce.common.email.domain;

import java.util.Arrays;

/**
 *
 * @ hui
 */
public class EmailTargetImpl implements EmailTarget {

    private static final long serialVersionUID = 1L;

    protected String[] bccAddresses;
    protected String[] ccAddresses;
    protected String emailAddress;

    public String[] getBCCAddresses() {
        return bccAddresses;
    }

    public String[] getCCAddresses() {
        return ccAddresses;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setBCCAddresses(String[] bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public void setCCAddresses(String[] ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bccAddresses);
        result = prime * result + Arrays.hashCode(ccAddresses);
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        EmailTargetImpl other = (EmailTargetImpl) obj;
        if (!Arrays.equals(bccAddresses, other.bccAddresses))
            return false;
        if (!Arrays.equals(ccAddresses, other.ccAddresses))
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        return true;
    }

}
