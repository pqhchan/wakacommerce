package com.wakacommerce.menu.domain;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface Menu extends Serializable, MultiTenantCloneable<Menu> {

    /**
     * Internal id of the menu.
     * @return
     */
    public Long getId();

    /**
     * Sets the id of the menu.
     * @param id
     */
    public void setId(Long id);

    /**
     * Returns the name of the menu.
     * @return
     */
    public String getName();

    /**
     * Sets the name of the menu.
     * @param name
     */
    public void setName(String name);

    /**
     * Returns the list of associated {@link MenuItem}s 
     * 
     * @return the featured products
     */
    public List<MenuItem> getMenuItems();

    /**
     * Sets the list of associated {@link MenuItem}s 
     */
    public void setMenuItems(@Nonnull List<MenuItem> menuItems);

}