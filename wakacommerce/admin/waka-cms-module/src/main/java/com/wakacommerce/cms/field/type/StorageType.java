package com.wakacommerce.cms.field.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class StorageType implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, StorageType> TYPES = new HashMap<String, StorageType>();

    public static final StorageType DATABASE = new StorageType("DATABASE", "数据库");
    public static final StorageType FILESYSTEM = new StorageType("FILESYSTEM", "文件系统");


    public static StorageType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public StorageType() {
        //do nothing
    }

    public StorageType(final String type, final String friendlyType) {
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
        StorageType other = (StorageType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
