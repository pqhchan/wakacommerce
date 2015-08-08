package com.wakacommerce.menu.domain;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.menu.type.MenuItemType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface MenuItem extends Serializable, MultiTenantCloneable<MenuItem> {

    public Long getId();

    public void setId(Long id);

    public String getLabel();

    public void setLabel(String label);

    public MenuItemType getMenuItemType();

    public void setMenuItemType(MenuItemType menuItemType);

    public String getActionUrl();

    public void setActionUrl(String actionUrl);

    public Media getImage();

    public void setImage(Media media);

    public Menu getParentMenu();

    public void setParentMenu(Menu menu);
    
    public Menu getLinkedMenu();

    public void setLinkedMenu(Menu menu);

    public void setSequence(BigDecimal sequence);

    public BigDecimal getSequence();

    public String getAltText();

    public void setAltText(String altText);

    public Page getLinkedPage();

    public void setLinkedPage(Page linkedPage);

    public String getCustomHtml();

    public void setCustomHtml(String customHtml);

    public String getDerivedUrl();

    public String getDerivedLabel();

}
