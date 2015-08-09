
package com.wakacommerce.openadmin.web.rulebuilder.enums;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.wakacommerce.common.WakaEnumType;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @ hui
 */
public abstract class AbstractRuleBuilderEnumOptionsExtensionListener implements RuleBuilderEnumOptionsExtensionListener {
    
    public String getOptionValues() {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, Class<? extends WakaEnumType>> entry : getValuesToGenerate().entrySet()) {
            try {
                sb.append("var ").append(entry.getKey()).append(" = [");
                
                int i = 0;
                Map<String, ? extends WakaEnumType> types = getTypes(entry.getValue());
                for (Entry<String, ? extends WakaEnumType> entry2 : types.entrySet()) {
                    sb.append("{ label : \"" + entry2.getValue().getFriendlyType() + "\"");
                    sb.append(", ");
                    sb.append(" name : \"" + entry2.getValue().getType() + "\" }");
                    if (++i < types.size()) {
                        sb.append(", ");
                    }
                }
                sb.append("]; \r\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, ? extends WakaEnumType> getTypes(Class<? extends WakaEnumType> clazz) {
        try {
            return (Map<String, ? extends WakaEnumType>) FieldUtils.readStaticField(clazz, "TYPES", true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Map<String, Class<? extends WakaEnumType>> getValuesToGenerate();

}
