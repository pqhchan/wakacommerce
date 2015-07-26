
package com.wakacommerce.core.web.checkout.section;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.BroadleafEnumerationType;

/**
 *Elbert Bautista (elbertbautista)
 */
public class CheckoutSectionViewType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, CheckoutSectionViewType> TYPES = new LinkedHashMap<String, CheckoutSectionViewType>();

    public static final CheckoutSectionViewType ORDER_INFO = new CheckoutSectionViewType("ORDER_INFO", "Order Info (Contact Info) View");
    public static final CheckoutSectionViewType BILLING_INFO = new CheckoutSectionViewType("BILLING_INFO", "Billing Info View");
    public static final CheckoutSectionViewType SHIPPING_INFO = new CheckoutSectionViewType("SHIPPING_INFO", "Shipping Info View");
    public static final CheckoutSectionViewType PAYMENT_INFO = new CheckoutSectionViewType("PAYMENT_INFO", "Payment Info View");

    public static CheckoutSectionViewType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public CheckoutSectionViewType() {
        //do nothing
    }

    public CheckoutSectionViewType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    public String getType() {
        return type;
    }

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
        CheckoutSectionViewType other = (CheckoutSectionViewType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
