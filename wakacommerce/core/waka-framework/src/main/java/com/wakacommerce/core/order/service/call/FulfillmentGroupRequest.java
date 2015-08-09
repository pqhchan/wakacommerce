
package com.wakacommerce.core.order.service.call;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.Phone;

import java.util.ArrayList;
import java.util.List;

public class FulfillmentGroupRequest {

    protected Address address;
    protected Order order;
    protected Phone phone;

    @Deprecated
    protected String method;
    @Deprecated
    protected String service;
    
    protected FulfillmentOption option;
    
    protected List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests = new ArrayList<FulfillmentGroupItemRequest>();

    protected FulfillmentType fulfillmentType;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
    
    public FulfillmentOption getOption() {
        return option;
    }
    
    public void setOption(FulfillmentOption option) {
        this.option = option;
    }

    public List<FulfillmentGroupItemRequest> getFulfillmentGroupItemRequests() {
        return fulfillmentGroupItemRequests;
    }

    public void setFulfillmentGroupItemRequests(List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests) {
        this.fulfillmentGroupItemRequests = fulfillmentGroupItemRequests;
    }

    public FulfillmentType getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(FulfillmentType fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

    @Deprecated
    public String getMethod() {
        return method;
    }
    
    @Deprecated
    public void setMethod(String method) {
        this.method = method;
    }

    @Deprecated
    public String getService() {
        return service;
    }
    
    @Deprecated
    public void setService(String service) {
        this.service = service;
    }

}
