package com.wakacommerce.openadmin.web.form.entity;

public class DefaultMainActions {
    
    public static final EntityFormAction ADD = new EntityFormAction(EntityFormAction.ADD)
        .withButtonClass("add-main-entity")
        .withUrlPostfix("/add")
        .withIconClass("icon-plus")
        .withDisplayText("添加");

}
