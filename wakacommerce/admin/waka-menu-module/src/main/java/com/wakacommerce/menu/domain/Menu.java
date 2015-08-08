package com.wakacommerce.menu.domain;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface Menu extends Serializable, MultiTenantCloneable<Menu> {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public List<MenuItem> getMenuItems();

    public void setMenuItems(@Nonnull List<MenuItem> menuItems);

}