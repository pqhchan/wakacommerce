 
package com.wakacommerce.cms.structure.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 * An extendible enumeration of offer rule types.<BR>
 * REQUEST - indicates a rule based on the incoming http request.<BR>
 * TIME - indicates a rule based on {@link com.wakacommerce.common.TimeDTO time}<br>
 * PRODUCT - indicates a rule based on {@link com.wakacommerce.core.catalog.domain.Product product}
 * CUSTOMER - indicates a rule based on {@link com.wakacommerce.profile.core.domain}
 */
public class StructuredContentRuleType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, StructuredContentRuleType> TYPES = new LinkedHashMap<String, StructuredContentRuleType>();

    public static final StructuredContentRuleType REQUEST = new StructuredContentRuleType("REQUEST", "Request");
    public static final StructuredContentRuleType TIME = new StructuredContentRuleType("TIME", "Time");
    public static final StructuredContentRuleType PRODUCT = new StructuredContentRuleType("PRODUCT", "Product");
    public static final StructuredContentRuleType CUSTOMER = new StructuredContentRuleType("CUSTOMER", "Customer");

    /**
     * Allows translation from the passed in String to a <code>StructuredContentRuleType</code>
     * @param type
     * @return The matching rule type
     */
    public static StructuredContentRuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public StructuredContentRuleType() {
        //do nothing
    }

    /**
     * Initialize the type and friendlyType
     * @param <code>type</code>
     * @param <code>friendlyType</code>
     */
    public StructuredContentRuleType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    /**
     * Sets the type
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    /**
     * Gets the type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the name of the type
     * @return
     */
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
        StructuredContentRuleType other = (StructuredContentRuleType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
