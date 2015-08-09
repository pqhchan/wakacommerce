
package com.wakacommerce.core.web.checkout.model;

import java.io.Serializable;

import com.wakacommerce.core.order.domain.PersonalMessage;
import com.wakacommerce.core.order.domain.PersonalMessageImpl;

/**
 *
 * @ hui
 */
public class MultiShipInstructionForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected String deliveryMessage;
    protected PersonalMessage personalMessage = new PersonalMessageImpl();
    protected Long fulfillmentGroupId;
    
    public String getDeliveryMessage() {
        return deliveryMessage;
    }
    
    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
    
    public PersonalMessage getPersonalMessage() {
        return personalMessage;
    }
    
    public void setPersonalMessage(PersonalMessage personalMessage) {
        this.personalMessage = personalMessage;
    }

    public Long getFulfillmentGroupId() {
        return fulfillmentGroupId;
    }

    public void setFulfillmentGroupId(Long id) {
        this.fulfillmentGroupId = id;
    }
    
}
