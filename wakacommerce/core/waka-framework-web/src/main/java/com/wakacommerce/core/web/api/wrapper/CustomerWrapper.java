package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerAttribute;
import com.wakacommerce.profile.core.service.CustomerService;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "customer")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CustomerWrapper extends BaseWrapper implements APIWrapper<Customer>, APIUnwrapper<Customer> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String realName;

    @XmlElement
    protected String emailAddress;

    @XmlElement(name = "customerAttribute")
    @XmlElementWrapper(name = "customerAttributes")
    protected List<CustomerAttributeWrapper> customerAttributes;

    @Override
    public void wrapDetails(Customer model, HttpServletRequest request) {
        this.id = model.getId();
        this.realName = model.getRealName();
        this.emailAddress = model.getEmailAddress();
        if (model.getCustomerAttributes() != null && !model.getCustomerAttributes().isEmpty()) {
            Map<String, CustomerAttribute> itemAttributes = model.getCustomerAttributes();
            this.customerAttributes = new ArrayList<CustomerAttributeWrapper>();
            Set<String> keys = itemAttributes.keySet();
            for (String key : keys) {
                CustomerAttributeWrapper customerAttributeWrapper =
                        (CustomerAttributeWrapper) context.getBean(CustomerAttributeWrapper.class.getName());
                customerAttributeWrapper.wrapDetails(itemAttributes.get(key), request);
                this.customerAttributes.add(customerAttributeWrapper);
            }
        }
    }

    @Override
    public void wrapSummary(Customer model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public Customer unwrap(HttpServletRequest request, ApplicationContext context) {
        CustomerService customerService = (CustomerService) context.getBean("blCustomerService");
        Customer customer = customerService.readCustomerById(this.id);
        customer.setId(this.id);
        customer.setRealName(this.realName);
        customer.setEmailAddress(this.emailAddress);
        if (customerAttributes != null) {
            for (CustomerAttributeWrapper customerAttributeWrapper : customerAttributes) {
                CustomerAttribute attribute = customerAttributeWrapper.unwrap(request, context);
                attribute.setCustomer(customer);
                customer.getCustomerAttributes().put(attribute.getName(), attribute);
            }
        }
        return customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<CustomerAttributeWrapper> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(List<CustomerAttributeWrapper> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }
}
