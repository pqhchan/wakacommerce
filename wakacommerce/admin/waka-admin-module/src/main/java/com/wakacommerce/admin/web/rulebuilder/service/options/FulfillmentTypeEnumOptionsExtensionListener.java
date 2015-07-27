
package com.wakacommerce.admin.web.rulebuilder.service.options;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.BroadleafEnumerationType;
import com.wakacommerce.common.time.HourOfDayType;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.openadmin.web.rulebuilder.enums.AbstractRuleBuilderEnumOptionsExtensionListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Rule Builder enum options provider for {@link HourOfDayType}
 * 
 *Andre Azzolini (apazzolini)
 */
@Component("blFulfillmentTypeOptionsExtensionListener")
public class FulfillmentTypeEnumOptionsExtensionListener extends AbstractRuleBuilderEnumOptionsExtensionListener {

    @Override
    protected Map<String, Class<? extends BroadleafEnumerationType>> getValuesToGenerate() {
        Map<String, Class<? extends BroadleafEnumerationType>> map = 
                new HashMap<String, Class<? extends BroadleafEnumerationType>>();
        
        map.put("blcOptions_FulfillmentType", FulfillmentType.class);
        
        return map;
    }

}
