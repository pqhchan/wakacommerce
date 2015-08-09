
package com.wakacommerce.common.payment;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class PaymentTransactionType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, PaymentTransactionType> TYPES = new LinkedHashMap<String, PaymentTransactionType>();

    public static final PaymentTransactionType AUTHORIZE = new PaymentTransactionType("AUTHORIZE", "Authorize");

    public static final PaymentTransactionType CAPTURE = new PaymentTransactionType("CAPTURE", "Capture");

    public static final PaymentTransactionType AUTHORIZE_AND_CAPTURE = new PaymentTransactionType("AUTHORIZE_AND_CAPTURE", "Authorize and Capture");

    public static final PaymentTransactionType SETTLED = new PaymentTransactionType("SETTLED", "Settled");

    public static final PaymentTransactionType REFUND = new PaymentTransactionType("REFUND", "Refund");

    public static final PaymentTransactionType VOID = new PaymentTransactionType("VOID", "Void");

    public static final PaymentTransactionType REVERSE_AUTH = new PaymentTransactionType("REVERSE_AUTH", "Reverse Auth");

    public static final PaymentTransactionType UNCONFIRMED = new PaymentTransactionType("UNCONFIRMED", "Not Confirmed");


    public static PaymentTransactionType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public PaymentTransactionType() {
        // do nothing
    }

    public PaymentTransactionType(String type, String friendlyType) {
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
        PaymentTransactionType other = (PaymentTransactionType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
