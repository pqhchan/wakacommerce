
package com.wakacommerce.core.offer.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class OfferItemRestrictionRuleType implements Serializable, WakaEnumType {
    
    private static final long serialVersionUID = 1L;

    private static final Map<String, OfferItemRestrictionRuleType> TYPES = new LinkedHashMap<String, OfferItemRestrictionRuleType>();

    public static final OfferItemRestrictionRuleType NONE = new OfferItemRestrictionRuleType("NONE", "None");
    public static final OfferItemRestrictionRuleType QUALIFIER = new OfferItemRestrictionRuleType("QUALIFIER", "Qualifier Only");
    public static final OfferItemRestrictionRuleType TARGET = new OfferItemRestrictionRuleType("TARGET", "Target Only");
    public static final OfferItemRestrictionRuleType QUALIFIER_TARGET = new OfferItemRestrictionRuleType("QUALIFIER_TARGET", "Qualifier And Target");

    public static OfferItemRestrictionRuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public OfferItemRestrictionRuleType() {
        //do nothing
    }

    public OfferItemRestrictionRuleType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
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
        OfferItemRestrictionRuleType other = (OfferItemRestrictionRuleType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
