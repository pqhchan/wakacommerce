package com.wakacommerce.common.sitemap.service.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wakacommerce.common.WakaEnumType;

/**
 *
 * @ hui
 */
public class SiteMapGeneratorType implements Serializable, WakaEnumType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, SiteMapGeneratorType> TYPES = new LinkedHashMap<String, SiteMapGeneratorType>();

    public static final SiteMapGeneratorType CATEGORY = new SiteMapGeneratorType("CATEGORY", "商品分类");
    public static final SiteMapGeneratorType PRODUCT = new SiteMapGeneratorType("PRODUCT", "商品");
    public static final SiteMapGeneratorType SKU = new SiteMapGeneratorType("SKU", "Sku");
    public static final SiteMapGeneratorType PAGE = new SiteMapGeneratorType("PAGE", "页面");
    public static final SiteMapGeneratorType CUSTOM = new SiteMapGeneratorType("CUSTOM", "自定义");

    public static SiteMapGeneratorType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public SiteMapGeneratorType() {
        //do nothing
    }

    public SiteMapGeneratorType(final String type, final String friendlyType) {
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
        SiteMapGeneratorType other = (SiteMapGeneratorType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
