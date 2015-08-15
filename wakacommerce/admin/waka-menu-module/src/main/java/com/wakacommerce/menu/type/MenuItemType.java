package com.wakacommerce.menu.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class MenuItemType implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, MenuItemType> TYPES = new HashMap<String, MenuItemType>();

    public static final MenuItemType LINK = new MenuItemType("LINK", "链接");
    public static final MenuItemType CATEGORY = new MenuItemType("CATEGORY", "分类");
    public static final MenuItemType PAGE = new MenuItemType("PAGE", "页面");
    public static final MenuItemType SUBMENU = new MenuItemType("SUBMENU", "子菜单");
    public static final MenuItemType PRODUCT = new MenuItemType("PRODUCT", "商品");
    public static final MenuItemType CUSTOM = new MenuItemType("CUSTOM", "自定义");


    public static MenuItemType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public MenuItemType() {
        //do nothing
    }

    public MenuItemType(final String type, final String friendlyType) {
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MenuItemType other = (MenuItemType) obj;
        if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
