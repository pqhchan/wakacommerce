
package com.wakacommerce.core.order.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class FulfillmentGroupStatusType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, FulfillmentGroupStatusType> TYPES = new LinkedHashMap<String, FulfillmentGroupStatusType>();

    @Deprecated
    public static final FulfillmentGroupStatusType SHIPPED = new FulfillmentGroupStatusType("SHIPPED", "Shipped");

    public static final FulfillmentGroupStatusType CANCELLED = new FulfillmentGroupStatusType("CANCELLED", "Cancelled");

    public static final FulfillmentGroupStatusType PROCESSING = new FulfillmentGroupStatusType("PROCESSING", "Processing");

    public static final FulfillmentGroupStatusType FULFILLED = new FulfillmentGroupStatusType("FULFILLED", "Fulfilled");

    public static final FulfillmentGroupStatusType PARTIALLY_FULFILLED = new FulfillmentGroupStatusType("PARTIALLY_FULFILLED", "Partially Fulfilled");

    public static final FulfillmentGroupStatusType DELIVERED = new FulfillmentGroupStatusType("DELIVERED", "Delivered");

    public static final FulfillmentGroupStatusType PARTIALLY_DELIVERED = new FulfillmentGroupStatusType("PARTIALLY_DELIVERED", "Partially Delivered");
    
    public static FulfillmentGroupStatusType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public FulfillmentGroupStatusType() {
        //do nothing
    }

    public FulfillmentGroupStatusType(final String type, final String friendlyType) {
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
        FulfillmentGroupStatusType other = (FulfillmentGroupStatusType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
