
package com.wakacommerce.menu.dao;

import java.util.List;

import com.wakacommerce.menu.domain.Menu;
import com.wakacommerce.menu.domain.MenuItem;

public interface MenuDao {

    public List<Menu> readAllMenus();

    public List<MenuItem> readAllMenuItems();

    public Menu readMenuById(Long menuId);

    public MenuItem readMenuItemById(Long menuItemId);

    public Menu readMenuByName(String menuName);

    public Menu saveMenu(Menu menu);

    public MenuItem saveMenuItem(MenuItem menuItem);

}