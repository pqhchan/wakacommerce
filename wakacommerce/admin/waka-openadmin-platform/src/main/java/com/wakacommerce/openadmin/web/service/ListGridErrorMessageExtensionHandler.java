  
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.web.form.component.ListGridRecord;

/**
 *
 * @ hui
 */
public interface ListGridErrorMessageExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType determineErrorMessageForEntity(Entity entity, ListGridRecord lgr);

}
