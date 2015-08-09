
package com.wakacommerce.common.i18n.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class ISOCodeStatusType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, ISOCodeStatusType> TYPES = new LinkedHashMap<String, ISOCodeStatusType>();

    public static final ISOCodeStatusType OFFICIALLY_ASSIGNED = new ISOCodeStatusType("OFFICIALLY_ASSIGNED", "Officially assigned: assigned to a country, territory, or area of geographical interest.");
    public static final ISOCodeStatusType USER_ASSIGNED = new ISOCodeStatusType("USER_ASSIGNED", "User-assigned: free for assignment at the disposal of users.");
    public static final ISOCodeStatusType EXCEPTIONALLY_RESERVED = new ISOCodeStatusType("EXCEPTIONALLY_RESERVED", "Exceptionally reserved: reserved on request for restricted use.");
    public static final ISOCodeStatusType TRANSITIONALLY_RESERVED = new ISOCodeStatusType("TRANSITIONALLY_RESERVED", "Transitionally reserved: deleted from ISO 3166-1 but reserved transitionally.");
    public static final ISOCodeStatusType INDETERMINATELY_RESERVED = new ISOCodeStatusType("INDETERMINATELY_RESERVED", "Indeterminately reserved: used in coding systems associated with ISO 3166-1.");
    public static final ISOCodeStatusType NOT_USED = new ISOCodeStatusType("NOT_USED", "Not used: not used in ISO 3166-1 in deference to intergovernmental intellectual property organisation names.");
    public static final ISOCodeStatusType UNASSIGNED = new ISOCodeStatusType("UNASSIGNED", "Unassigned: free for assignment by the ISO 3166/MA only.");

    public static ISOCodeStatusType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public ISOCodeStatusType() {
        //do nothing
    }

    public ISOCodeStatusType(final String type, final String friendlyType) {
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
        ISOCodeStatusType other = (ISOCodeStatusType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}

