
package com.wakacommerce.common.i18n.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.i18n.service.type.ISOCodeStatusType;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;


/**
 *Elbert Bautista (elbertbautista)
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ISO_COUNTRY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "ISOCountryImpl_baseCountry")
public class ISOCountryImpl implements ISOCountry, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ALPHA_2")
    @AdminPresentation(friendlyName = "ISOCountryImpl_Alpha2", order=1, group = "ISOCountryImpl_Details", prominent = true)
    protected String alpha2;

    @Column(name = "NAME")
    @AdminPresentation(friendlyName = "ISOCountryImpl_Name", order=2, group = "ISOCountryImpl_Details", prominent = true)
    protected String name;

    @Column(name = "ALPHA_3")
    protected String alpha3;

    @Column(name = "NUMERIC_CODE")
    protected Integer numericCode;

    @Column(name = "STATUS")
    protected String status;

    @Override
    public String getAlpha2() {
        return alpha2;
    }

    @Override
    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAlpha3() {
        return alpha3;
    }

    @Override
    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    @Override
    public Integer getNumericCode() {
        return numericCode;
    }

    @Override
    public void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public ISOCodeStatusType getStatus() {
        return ISOCodeStatusType.getInstance(status);
    }

    @Override
    public void setStatus(ISOCodeStatusType status) {
        this.status = status == null ? null : status.getType();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        ISOCountryImpl other = (ISOCountryImpl) obj;
        if (alpha2 == null) {
            if (other.alpha2 != null)
                return false;
        } else if (!alpha2.equals(other.alpha2))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (alpha3 == null) {
            if (other.alpha3 != null)
                return false;
        } else if (!alpha3.equals(other.alpha3))
            return false;
        if (numericCode == null) {
            if (other.numericCode != null)
                return false;
        } else if (!numericCode.equals(other.numericCode))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alpha2 == null) ? 0 : alpha2.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((alpha3 == null) ? 0 : alpha3.hashCode());
        result = prime * result + ((numericCode == null) ? 0 : numericCode.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

}
