
package com.wakacommerce.menu.service;

import java.util.List;

import com.wakacommerce.menu.domain.Menu;
import com.wakacommerce.menu.domain.MenuItem;
import com.wakacommerce.menu.dto.MenuItemDTO;

public interface MenuService {

    public Menu findMenuById(Long menuId);

    public Menu findMenuByName(String menuName);

    public MenuItem findMenuItemById(Long menuItemId);

    public List<MenuItemDTO> constructMenuItemDTOsForMenu(Menu menu);
}