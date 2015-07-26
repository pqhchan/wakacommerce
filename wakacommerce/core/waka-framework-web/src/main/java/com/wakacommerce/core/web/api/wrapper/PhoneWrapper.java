
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.profile.core.domain.Phone;
import com.wakacommerce.profile.core.service.PhoneService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a JAXB wrapper around Phone.
 *
 * User: Elbert Bautista
 * Date: 4/24/12
 */
@XmlRootElement(name = "phone")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PhoneWrapper extends BaseWrapper implements APIWrapper<Phone>, APIUnwrapper<Phone> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String phoneNumber;

    @XmlElement
    protected Boolean isActive;

    @XmlElement
    protected Boolean isDefault;

    @Override
    public void wrapDetails(Phone model, HttpServletRequest request) {
        this.id = model.getId();
        this.phoneNumber = model.getPhoneNumber();
        this.isActive = model.isActive();
        this.isDefault = model.isDefault();
    }

    @Override
    public void wrapSummary(Phone model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public Phone unwrap(HttpServletRequest request, ApplicationContext appContext) {
        PhoneService phoneService = (PhoneService) appContext.getBean("blPhoneService");
        Phone phone = phoneService.create();
        phone.setId(this.id);

        if (this.isActive != null) {
            phone.setActive(this.isActive);
        }

        if (this.isDefault != null) {
            phone.setDefault(this.isDefault);
        }

        phone.setPhoneNumber(this.phoneNumber);

        return phone;
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    
    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    
    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    
    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
