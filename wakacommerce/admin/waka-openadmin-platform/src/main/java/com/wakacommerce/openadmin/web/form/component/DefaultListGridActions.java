package com.wakacommerce.openadmin.web.form.component;


/**
 *
 * @ hui
 */
public class DefaultListGridActions {
    
    public static final ListGridAction ADD = new ListGridAction(ListGridAction.ADD)
        .withButtonClass("sub-list-grid-add")
        .withUrlPostfix("/add")
        .withIconClass("icon-plus")
        .withDisplayText("添加");
    
    public static final ListGridAction REORDER = new ListGridAction(ListGridAction.REORDER)
        .withButtonClass("sub-list-grid-reorder")
        .withUrlPostfix("/update")
        .withIconClass("icon-move")
        .withDisplayText("重排");

    // Actions for row-level
    public static final ListGridAction REMOVE = new ListGridAction(ListGridAction.REMOVE)
        .withButtonClass("sub-list-grid-remove")
        .withUrlPostfix("/delete")
        .withIconClass("icon-remove")
        .withDisplayText("删除");
    
    public static final ListGridAction UPDATE = new ListGridAction(ListGridAction.UPDATE)
        .withButtonClass("sub-list-grid-update")
        .withIconClass("icon-pencil")
        .withDisplayText("编辑");

    public static final ListGridAction VIEW = new ListGridAction(ListGridAction.VIEW)
        .withButtonClass("sub-list-grid-view")
        .withIconClass("icon-book")
        .withDisplayText("查看")
        .withUrlPostfix("/view")
        .withForListGridReadOnly(true);


    public static final ListGridAction PREVIEW = new ListGridAction("PREVIEW")
            .withButtonClass("workflow-preview")
            .withIconClass("icon-eye-open")
            .withDisplayText("Workflow_button_preview");
    
}
