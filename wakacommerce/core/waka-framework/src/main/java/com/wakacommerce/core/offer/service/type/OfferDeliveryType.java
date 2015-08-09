
package com.wakacommerce.core.offer.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class OfferDeliveryType implements Serializable, WakaEnumType, Comparable<OfferDeliveryType> {
    
    private static final long serialVersionUID = 1L;

    private static final Map<String, OfferDeliveryType> TYPES = new LinkedHashMap<String, OfferDeliveryType>();

    public static final OfferDeliveryType AUTOMATIC = new OfferDeliveryType("AUTOMATIC", "Automatically", 1000);
    public static final OfferDeliveryType CODE = new OfferDeliveryType("CODE", "Using Shared Code", 2000);
    public static final OfferDeliveryType MANUAL = new OfferDeliveryType("MANUAL", "Via Application or Shared Code", 3000);

    public static OfferDeliveryType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;
    private int order;

    public OfferDeliveryType() {
        //do nothing
    }

    public OfferDeliveryType(final String type, final String friendlyType, int order) {
        this.friendlyType = friendlyType;
        setType(type);
        setOrder(order);
    }

    public void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    public String getType() {
        return type;
    }

    public String getFriendlyType() {
        return friendlyType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
        OfferDeliveryType other = (OfferDeliveryType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public int compareTo(OfferDeliveryType arg0) {
        return this.order - arg0.order;
    }

}
