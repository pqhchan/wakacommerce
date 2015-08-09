

package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.profile.core.domain.CustomerAttribute;
import com.wakacommerce.profile.core.domain.CustomerAttributeImpl;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "customerAttribute")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CustomerAttributeWrapper extends BaseWrapper implements
        APIWrapper<CustomerAttribute>, APIUnwrapper<CustomerAttribute> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String value;

    @XmlElement
    protected Long customerId;

    @Override
    public void wrapDetails(CustomerAttribute model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.value = model.getValue();
        this.customerId = model.getCustomer().getId();
    }

    @Override
    public void wrapSummary(CustomerAttribute model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public CustomerAttribute unwrap(HttpServletRequest request, ApplicationContext context) {
        CustomerAttribute attribute = new CustomerAttributeImpl();
        attribute.setId(id);
        attribute.setName(name);
        attribute.setValue(value);
        return attribute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
