  
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.form.component.ListGrid;

/**
 *
 * @ hui
 */
public interface AdminAbstractListGridExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType addAdditionalRowAction(ListGrid listGrid);
}
