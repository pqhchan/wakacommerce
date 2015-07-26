
package com.wakacommerce.menu.service;

import java.util.List;

import com.wakacommerce.menu.domain.Menu;
import com.wakacommerce.menu.domain.MenuItem;
import com.wakacommerce.menu.dto.MenuItemDTO;

public interface MenuService {

    /**
     * Returns the menu matching the passed in id. 
     * @param menuId
     * @return
     */
    public Menu findMenuById(Long menuId);

    /**
     * Returns the menu matching the passed in name. 
     * @param menuName
     * @return
     */
    public Menu findMenuByName(String menuName);

    /**
     * Returns the menu item matching the passed in id.
     * @param menuItemId
     * @return
     */
    public MenuItem findMenuItemById(Long menuItemId);

    /**
     * A Utility method that constructs generic MenuItemDTOs that are not dependent on a Menu Item Type.
     * Allows for ease of use when building the front-end.
     *
     * @param menu
     * @return
     */
    public List<MenuItemDTO> constructMenuItemDTOsForMenu(Menu menu);
}