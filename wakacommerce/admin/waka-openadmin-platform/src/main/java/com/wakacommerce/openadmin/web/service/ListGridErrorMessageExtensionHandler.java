  
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.web.form.component.ListGridRecord;

/**
 * An extension handler to allow a custom error key or error message to be added to the ListGridRecord.
 *kellytisdell
 *
 */
public interface ListGridErrorMessageExtensionHandler extends ExtensionHandler {

    /**
     * Allows the extension handler to determine a custom error message or error message key for the entity. 
     * Implementors should determine if they can handle the entity in question. If not, they should return 
     * ExtensionResultStatusType.NOT_HANDLED.
     * 
     * Otherwise, they should either set the error message or the error key on the ListGrid on the entity. If both 
     * are set the error message will win.
     * 
     * Implementors can use the WakaRequestContext to try to determine Locale, or get a MessageSource, etc.
     * 
     * @param entity
     * @param lgr
     * @return
     */
    public ExtensionResultStatusType determineErrorMessageForEntity(Entity entity, ListGridRecord lgr);

}
