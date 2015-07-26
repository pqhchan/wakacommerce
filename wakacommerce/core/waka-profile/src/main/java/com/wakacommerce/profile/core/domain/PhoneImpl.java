/*
 * #%L
 * BroadleafCommerce Profile
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.profile.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PHONE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
public class PhoneImpl implements Phone {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PhoneId")
    @GenericGenerator(
        name="PhoneId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PhoneImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.profile.core.domain.PhoneImpl")
        }
    )
    @Column(name = "PHONE_ID")
    protected Long id;

    @Column(name = "COUNTRY_CODE")
    @AdminPresentation(friendlyName = "PhoneImpl_Country_Code", order=1, group = "PhoneImpl_Phone")
    protected String countryCode;

    @Column(name = "PHONE_NUMBER", nullable=false)
    @AdminPresentation(friendlyName = "PhoneImpl_Phone_Number", order=2, group = "PhoneImpl_Phone")
    protected String phoneNumber;

    @Column(name = "EXTENSION")
    @AdminPresentation(friendlyName = "PhoneImpl_Extension", order=3, group = "PhoneImpl_Phone")
    protected String extension;

    @Column(name = "IS_DEFAULT")
    @AdminPresentation(friendlyName = "PhoneImpl_Default_Phone", order=4, group = "PhoneImpl_Phone")
    protected boolean isDefault = false;

    @Column(name = "IS_ACTIVE")
    @AdminPresentation(friendlyName = "PhoneImpl_Active_Phone", order=5, group = "PhoneImpl_Phone")
    protected boolean isActive = true;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isActive ? 1231 : 1237);
        result = prime * result + (isDefault ? 1231 : 1237);
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        PhoneImpl other = (PhoneImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (isActive != other.isActive)
            return false;
        if (isDefault != other.isDefault)
            return false;
        if (countryCode == null) {
            if (other.countryCode != null)
                return false;
        } else if (!countryCode.equals(other.countryCode))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        return true;
    }
}
