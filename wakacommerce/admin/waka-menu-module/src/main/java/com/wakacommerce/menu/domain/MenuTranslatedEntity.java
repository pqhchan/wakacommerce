
package com.wakacommerce.menu.domain;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.i18n.domain.TranslatedEntity;

/**
 *Elbert Bautista (elbertbautista)
 */
@Component("blMenuTranslatedEntity")
public class MenuTranslatedEntity extends TranslatedEntity {

    private static final long serialVersionUID = -1;

    public static final TranslatedEntity MENU = new TranslatedEntity("com.wakacommerce.menu.domain.Menu", "Menu");
    public static final TranslatedEntity MENU_ITEM = new TranslatedEntity("com.wakacommerce.menu.domain.MenuItem", "MenuItem");

}
