
package com.wakacommerce.openadmin.web.rulebuilder.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;

import java.util.List;


/**
 *
 * @ hui
 */
public interface RuleBuilderFieldServiceExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType addFields(List<FieldData> fields, String name);

}
