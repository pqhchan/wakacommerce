
package com.wakacommerce.admin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.presentation.RuleIdentifier;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;
import com.wakacommerce.openadmin.web.rulebuilder.service.AbstractRuleBuilderFieldService;

/**
 * An implementation of a RuleBuilderFieldService
 * that constructs metadata necessary
 * to build the supported fields for an Order entity
 *
 *  
 */
@Service("blOrderFieldService")
public class OrderFieldServiceImpl extends AbstractRuleBuilderFieldService {

    @Override
    public void init() {
        fields.add(new FieldData.Builder()
                .label("rule_orderCurrencyCode")
                .name("currency.currencyCode")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        fields.add(new FieldData.Builder()
                .label("rule_orderSubtotal")
                .name("subTotal")
                .operators("blcOperators_Numeric")
                .options("[]")
                .type(SupportedFieldType.MONEY)
                .build());
    }

    @Override
    public String getName() {
        return RuleIdentifier.ORDER;
    }

    @Override
    public String getDtoClassName() {
        return "com.wakacommerce.core.order.domain.OrderImpl";
    }
}
