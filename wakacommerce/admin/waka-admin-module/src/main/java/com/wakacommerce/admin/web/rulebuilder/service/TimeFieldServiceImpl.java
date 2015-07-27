
package com.wakacommerce.admin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.presentation.RuleIdentifier;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;
import com.wakacommerce.openadmin.web.rulebuilder.service.AbstractRuleBuilderFieldService;

/**
 * An implementation of a RuleBuilderFieldService
 * that constructs metadata necessary
 * to build the supported fields for a Time entity
 *
 *Andre Azzolini (apazzolini)
 */
@Service("blTimeFieldService")
public class TimeFieldServiceImpl extends AbstractRuleBuilderFieldService {

    @Override
    public void init() {
        fields.add(new FieldData.Builder()
                .label("rule_timeHourOfDay")
                .name("hour")
                .operators("blcOperators_Enumeration")
                .options("blcOptions_HourOfDay")
                .type(SupportedFieldType.BROADLEAF_ENUMERATION)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_timeDayOfWeek")
                .name("dayOfWeek")
                .operators("blcOperators_Enumeration")
                .options("blcOptions_DayOfWeek")
                .type(SupportedFieldType.BROADLEAF_ENUMERATION)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_timeMonth")
                .name("month")
                .operators("blcOperators_Enumeration")
                .options("blcOptions_Month")
                .type(SupportedFieldType.BROADLEAF_ENUMERATION)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_timeDayOfMonth")
                .name("dayOfMonth")
                .operators("blcOperators_Enumeration")
                .options("blcOptions_DayOfMonth")
                .type(SupportedFieldType.BROADLEAF_ENUMERATION)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_timeMinute")
                .name("minute")
                .operators("blcOperators_Enumeration")
                .options("blcOptions_Minute")
                .type(SupportedFieldType.BROADLEAF_ENUMERATION)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_timeDate")
                .name("date")
                .operators("blcOperators_Date")
                .options("[]")
                .type(SupportedFieldType.DATE)
                .build());
    }

    @Override
    public String getName() {
        return RuleIdentifier.TIME;
    }

    @Override
    public String getDtoClassName() {
        return "com.wakacommerce.common.TimeDTO";
    }
}
