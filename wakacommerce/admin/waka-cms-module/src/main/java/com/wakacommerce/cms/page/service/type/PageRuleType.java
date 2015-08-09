 
package com.wakacommerce.cms.page.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class PageRuleType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, PageRuleType> TYPES = new LinkedHashMap<String, PageRuleType>();

    public static final PageRuleType REQUEST = new PageRuleType("REQUEST", "Request");
    public static final PageRuleType TIME = new PageRuleType("TIME", "Time");
    public static final PageRuleType PRODUCT = new PageRuleType("PRODUCT", "Product");
    public static final PageRuleType CUSTOMER = new PageRuleType("CUSTOMER", "Customer");

    public static PageRuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public PageRuleType() {
        //do nothing
    }

    public PageRuleType(final String type, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    public void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        PageRuleType other = (PageRuleType) obj;
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

}
