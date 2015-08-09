

package com.wakacommerce.common.payment;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;


/**
 *
 * @ hui
 */
public class PaymentGatewayType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, PaymentGatewayType> TYPES = new LinkedHashMap<String, PaymentGatewayType>();

    public static final PaymentGatewayType TEMPORARY  = new PaymentGatewayType("Temporary", "This is a temporary Order Payment");
    public static final PaymentGatewayType PASSTHROUGH  = new PaymentGatewayType("Passthrough", "Passthrough Payment");

    public static PaymentGatewayType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public PaymentGatewayType() {
        // do nothing
    }

    public PaymentGatewayType(String type, String friendlyType) {
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
        if (!TYPES.containsKey(type)){
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
        PaymentGatewayType other = (PaymentGatewayType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
