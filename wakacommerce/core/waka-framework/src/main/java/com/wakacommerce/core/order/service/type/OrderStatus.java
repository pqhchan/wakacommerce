
package com.wakacommerce.core.order.service.type;

import com.wakacommerce.common.WakaEnumType;
import com.wakacommerce.core.order.domain.Order;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 *
 * @ hui
 */
public class OrderStatus implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final LinkedHashMap<String, OrderStatus> TYPES = new LinkedHashMap<String, OrderStatus>();

    public static final OrderStatus NAMED = new OrderStatus("NAMED", "Named");
    public static final OrderStatus QUOTE = new OrderStatus("QUOTE", "Quote");

    public static final OrderStatus IN_PROCESS = new OrderStatus("IN_PROCESS", "In Process");

    public static final OrderStatus SUBMITTED = new OrderStatus("SUBMITTED", "Submitted");
    public static final OrderStatus CANCELLED = new OrderStatus("CANCELLED", "Cancelled");

    public static final OrderStatus CSR_OWNED = new OrderStatus("CSR_OWNED", "Owned by CSR");


    public static OrderStatus getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public OrderStatus() {
        //do nothing
    }

    public OrderStatus(final String type, final String friendlyType) {
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
        OrderStatus other = (OrderStatus) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
