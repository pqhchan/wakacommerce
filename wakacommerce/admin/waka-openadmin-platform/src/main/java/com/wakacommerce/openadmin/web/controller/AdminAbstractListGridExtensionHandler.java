  
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.form.component.ListGrid;

/**
 * Extension handler for {@link com.wakacommerce.openadmin.web.form.component.ListGrid}.
 */
public interface AdminAbstractListGridExtensionHandler extends ExtensionHandler {

    /**
     * This can be used to add {@link com.wakacommerce.openadmin.web.form.component.ListGridAction}s to the {@link ListGrid}
     * @param listGrid
     * @return
     */
    public ExtensionResultStatusType addAdditionalRowAction(ListGrid listGrid);
}
