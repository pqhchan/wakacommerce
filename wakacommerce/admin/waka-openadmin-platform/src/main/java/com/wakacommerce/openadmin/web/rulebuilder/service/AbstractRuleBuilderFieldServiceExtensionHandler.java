
package com.wakacommerce.openadmin.web.rulebuilder.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;

import java.util.List;


/**
 * 
 */
public class AbstractRuleBuilderFieldServiceExtensionHandler extends AbstractExtensionHandler
        implements RuleBuilderFieldServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType addFields(List<FieldData> fields, String name) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
