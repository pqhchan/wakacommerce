
package com.wakacommerce.admin.web.rulebuilder.service.options;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.WakaEnumType;
import com.wakacommerce.common.time.HourOfDayType;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.openadmin.web.rulebuilder.enums.AbstractRuleBuilderEnumOptionsExtensionListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Rule Builder enum options provider for {@link HourOfDayType}
 * 
 * 
 */
@Component("blInventoryTypeOptionsExtensionListener")
public class InventoryTypeEnumOptionsExtensionListener extends AbstractRuleBuilderEnumOptionsExtensionListener {

    /**
     * Overridden to remove deprecated options
     */
    @Override
    protected Map<String, ? extends WakaEnumType> getTypes(Class<? extends WakaEnumType> clazz) {
        
        try {
            Map<String, ? extends WakaEnumType> options =
                    (Map<String, ? extends WakaEnumType>) FieldUtils.readStaticField(clazz, "TYPES", true);
            options.remove("NONE");
            options.remove("BASIC");
            return options;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected Map<String, Class<? extends WakaEnumType>> getValuesToGenerate() {
        Map<String, Class<? extends WakaEnumType>> map = 
                new HashMap<String, Class<? extends WakaEnumType>>();
        
        map.put("blcOptions_InventoryType", InventoryType.class);
        
        return map;
    }

}
