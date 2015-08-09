
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.web.form.component.ListGrid;
import com.wakacommerce.openadmin.web.form.component.ListGridRecord;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;

/**
 *
 * @ hui
 */
public interface FormBuilderExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType modifyUnpopulatedEntityForm(EntityForm ef);

    public ExtensionResultStatusType modifyPopulatedEntityForm(EntityForm ef, Entity entity);

    public ExtensionResultStatusType modifyDetailEntityForm(EntityForm ef);

    public ExtensionResultStatusType modifyListGridRecord(String className, ListGridRecord record, Entity entity);

    public ExtensionResultStatusType addAdditionalFormActions(EntityForm entityForm);

}
