package com.wakacommerce.menu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String label;
    protected String url;
    protected String imageUrl;
    protected String altText;
    protected List<MenuItemDTO> submenu = new ArrayList<MenuItemDTO>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public List<MenuItemDTO> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<MenuItemDTO> submenu) {
        this.submenu = submenu;
    }

}
