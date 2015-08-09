
package com.wakacommerce.common.currency.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @ hui
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CURRENCY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blCMSElements")
@AdminPresentationClass(friendlyName = "BroadleafCurrencyImpl_baseCurrency")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class BroadleafCurrencyImpl implements BroadleafCurrency, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CURRENCY_CODE")
    @AdminPresentation(friendlyName = "BroadleafCurrencyImpl_Currency_Code",
            order = 1, group = "BroadleafCurrencyImpl_Details", prominent = true, gridOrder = 2000)
    protected String currencyCode;

    @Column(name = "FRIENDLY_NAME")
    @AdminPresentation(friendlyName = "BroadleafCurrencyImpl_Name", order = 2, group = "BroadleafCurrencyImpl_Details",
            prominent = true, gridOrder = 1000)
    protected String friendlyName;

    @Column(name = "DEFAULT_FLAG")
    @AdminPresentation(friendlyName = "BroadleafCurrencyImpl_Is_Default", group = "BroadleafCurrencyImpl_Details", excluded = true)
    protected Boolean defaultFlag = false;

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public void setCurrencyCode(String code) {
        this.currencyCode = code;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public boolean getDefaultFlag() {
        if (defaultFlag == null) {
            return false;
        }
        return defaultFlag.booleanValue();
    }

    @Override
    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = new Boolean(defaultFlag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) {
            return false;
        }

        BroadleafCurrencyImpl currency = (BroadleafCurrencyImpl) o;

        if (currencyCode != null ? !currencyCode.equals(currency.currencyCode) : currency.currencyCode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = currencyCode != null ? currencyCode.hashCode() : 0;
        return result;
    }

    @Override
    public String getMainEntityName() {
        if (getFriendlyName() != null) {
            return getFriendlyName() + " (" + getCurrencyCode() + ")";
        } else {
            return getCurrencyCode();
        }
    }
}
