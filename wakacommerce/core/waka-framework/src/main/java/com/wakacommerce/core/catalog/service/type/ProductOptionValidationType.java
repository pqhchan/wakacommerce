
package com.wakacommerce.core.catalog.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class ProductOptionValidationType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, ProductOptionValidationType> TYPES = new LinkedHashMap<String, ProductOptionValidationType>();

    public static final ProductOptionValidationType REGEX = new ProductOptionValidationType("REGEX", "Regular Expression");

    public static ProductOptionValidationType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public ProductOptionValidationType() {
        //do nothing
    }

    public ProductOptionValidationType(final String type, final String friendlyType) {
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
        ProductOptionValidationType other = (ProductOptionValidationType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
