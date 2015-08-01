
package com.wakacommerce.menu.processor;

import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.menu.domain.Menu;
import com.wakacommerce.menu.service.MenuService;

import javax.annotation.Resource;

/**
 * A Thymeleaf processor that will add a list of MenuItemDTOs to the model.
 * 
 * It accepts a menuName or menuId. The precedence is that a menuId
 * will honored first, followed by a menuName.
 * An extension manager may override the resulting menu if configured to do so.
 *
 * 
 */
@Component("blMenuProcessor")
public class MenuProcessor extends AbstractModelVariableModifierProcessor {

    @Resource(name = "blMenuService")
    protected MenuService menuService;

    @Resource(name = "blMenuProcessorExtensionManager")
    protected MenuProcessorExtensionManager extensionManager;

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     */
    public MenuProcessor() {
        super("menu");
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        String resultVar = element.getAttributeValue("resultVar");
        String menuName = element.getAttributeValue("menuName");
        String menuId = element.getAttributeValue("menuId");

        final Menu menu;

        if (menuId != null) {
            menu = menuService.findMenuById(Long.parseLong(menuId));
        } else {
            menu = menuService.findMenuByName(menuName);
        }

        if (menu != null) {
            addToModel(arguments, resultVar, menuService.constructMenuItemDTOsForMenu(menu));
            extensionManager.getProxy().addAdditionalFieldsToModel(arguments, element);
        }

    }
}
