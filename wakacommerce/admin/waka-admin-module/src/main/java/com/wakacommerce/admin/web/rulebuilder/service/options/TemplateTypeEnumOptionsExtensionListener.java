
package com.wakacommerce.admin.web.rulebuilder.service.options;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.WakaEnumType;
import com.wakacommerce.common.template.TemplateType;
import com.wakacommerce.openadmin.web.rulebuilder.enums.AbstractRuleBuilderEnumOptionsExtensionListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Rule Builder enum options provider for {@link TemplateType}
 * 
 * Used in Content Tests
 * 
 * 
 */
@Component("blTemplateTypeOptionsExtensionListener")
public class TemplateTypeEnumOptionsExtensionListener extends AbstractRuleBuilderEnumOptionsExtensionListener {

    @Override
    protected Map<String, Class<? extends WakaEnumType>> getValuesToGenerate() {
        Map<String, Class<? extends WakaEnumType>> map = 
                new HashMap<String, Class<? extends WakaEnumType>>();
        
        map.put("blcOptions_TemplateType", TemplateType.class);
        
        return map;
    }

}
