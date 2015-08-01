package com.wakacommerce.cms.field.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FieldType implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, FieldType> TYPES = new HashMap<String, FieldType>();

    public static final FieldType BOOLEAN = new FieldType("BOOLEAN", "布尔值");
    public static final FieldType DATE = new FieldType("DATE", "日期");
    public static final FieldType TIME = new FieldType("TIME", "时间");
    public static final FieldType INTEGER = new FieldType("INTEGER", "整数");
    public static final FieldType DECIMAL = new FieldType("DECIMAL", "数值");
    public static final FieldType STRING = new FieldType("STRING", "字符串");
    public static final FieldType RICH_TEXT = new FieldType("RICH_TEXT", "富文本");
    public static final FieldType HTML = new FieldType("HTML", "HTML");
    public static final FieldType ENUMERATION = new FieldType("ENUMERATION", "枚举值");

    public static FieldType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public FieldType() {
        //do nothing
    }

    public FieldType(final String type, final String friendlyType) {
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
        int result = 1;
        result = 31 * result + ((type == null) ? 0 : type.hashCode());
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
        FieldType other = (FieldType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
