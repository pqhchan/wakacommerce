package com.wakacommerce.openadmin.web.form.entity;

public class DefaultEntityFormActions {
    
    public static final EntityFormAction SAVE = new EntityFormAction(EntityFormAction.SAVE)
        .withButtonType("submit")
        .withButtonClass("submit-button")
        .withDisplayText("保存");

    public static final EntityFormAction DELETE = new EntityFormAction(EntityFormAction.DELETE)
        .withButtonClass("delete-button alert")
        .withDisplayText("删除");
    
    public static final EntityFormAction PREVIEW = new EntityFormAction(EntityFormAction.PREVIEW)
        .withButtonClass("preview-button")
        .withDisplayText("预览");

}
