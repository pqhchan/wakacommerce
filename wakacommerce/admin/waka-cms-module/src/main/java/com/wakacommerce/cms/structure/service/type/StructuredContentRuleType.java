package com.wakacommerce.cms.structure.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

public class StructuredContentRuleType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, StructuredContentRuleType> TYPES = new LinkedHashMap<String, StructuredContentRuleType>();

    public static final StructuredContentRuleType REQUEST = new StructuredContentRuleType("REQUEST", "请求");
    public static final StructuredContentRuleType TIME = new StructuredContentRuleType("TIME", "时间");
    public static final StructuredContentRuleType PRODUCT = new StructuredContentRuleType("PRODUCT", "货品");
    public static final StructuredContentRuleType CUSTOMER = new StructuredContentRuleType("CUSTOMER", "客户");

    public static StructuredContentRuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public StructuredContentRuleType() {
        //do nothing
    }

    public StructuredContentRuleType(final String type, final String friendlyType) {
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
        StructuredContentRuleType other = (StructuredContentRuleType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
