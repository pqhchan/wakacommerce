package com.wakacommerce.admin.web.rulebuilder.service.options;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.WakaEnumType;
import com.wakacommerce.common.time.DayOfMonthType;
import com.wakacommerce.common.time.DayOfWeekType;
import com.wakacommerce.common.time.HourOfDayType;
import com.wakacommerce.common.time.MinuteType;
import com.wakacommerce.common.time.MonthType;
import com.wakacommerce.openadmin.web.rulebuilder.enums.AbstractRuleBuilderEnumOptionsExtensionListener;

import java.util.HashMap;
import java.util.Map;

@Component("blTimeOptionsExtensionListener")
public class TimeEnumOptionsExtensionListener extends AbstractRuleBuilderEnumOptionsExtensionListener {

    @Override
    protected Map<String, Class<? extends WakaEnumType>> getValuesToGenerate() {
        Map<String, Class<? extends WakaEnumType>> map = 
                new HashMap<String, Class<? extends WakaEnumType>>();
        
        map.put("blcOptions_HourOfDay", HourOfDayType.class);
        map.put("blcOptions_DayOfWeek", DayOfWeekType.class);
        map.put("blcOptions_Month", MonthType.class);
        map.put("blcOptions_DayOfMonth", DayOfMonthType.class);
        map.put("blcOptions_Minute", MinuteType.class);
        
        return map;
    }

}
