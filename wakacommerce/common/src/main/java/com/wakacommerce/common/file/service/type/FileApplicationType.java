package com.wakacommerce.common.file.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

public class FileApplicationType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, FileApplicationType> TYPES = new LinkedHashMap<String, FileApplicationType>();

    public static final FileApplicationType ALL = new FileApplicationType("ALL", "All"); // fall-through
    public static final FileApplicationType IMAGE = new FileApplicationType("IMAGE", "Images");
    public static final FileApplicationType STATIC = new FileApplicationType("STATIC", "Static Assets");
    public static final FileApplicationType SITE_MAP = new FileApplicationType("SITEMAP", "Site Map");

    public static FileApplicationType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public FileApplicationType() {
        //do nothing
    }

    public FileApplicationType(final String type, final String friendlyType) {
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
        FileApplicationType other = (FileApplicationType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
