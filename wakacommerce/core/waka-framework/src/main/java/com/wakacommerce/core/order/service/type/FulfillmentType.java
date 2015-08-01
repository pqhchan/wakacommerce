
package com.wakacommerce.core.order.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 * An extendible enumeration of fulfillment group types.
 * 
 *  
 *
 */
public class FulfillmentType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, FulfillmentType> TYPES = new LinkedHashMap<String, FulfillmentType>();

    public static final FulfillmentType DIGITAL = new FulfillmentType("DIGITAL", "Digital");
    public static final FulfillmentType PHYSICAL_SHIP = new FulfillmentType("PHYSICAL_SHIP", "Physical Ship");
    public static final FulfillmentType PHYSICAL_PICKUP = new FulfillmentType("PHYSICAL_PICKUP", "Physical Pickup");
    public static final FulfillmentType PHYSICAL_PICKUP_OR_SHIP = new FulfillmentType("PHYSICAL_PICKUP_OR_SHIP", "Physical Pickup or Ship");
    public static final FulfillmentType GIFT_CARD = new FulfillmentType("GIFT_CARD", "Gift Card");

    public static FulfillmentType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public FulfillmentType() {
        //do nothing
    }

    public FulfillmentType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FulfillmentType other = (FulfillmentType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
