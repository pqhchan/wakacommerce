
package com.wakacommerce.openadmin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *bpolster
 */
@Service("blRuleBuilderFieldServiceExtensionManager")
public class RuleBuilderFieldServiceExtensionManager extends ExtensionManager<RuleBuilderFieldServiceExtensionHandler> {

    public RuleBuilderFieldServiceExtensionManager() {
        super(RuleBuilderFieldServiceExtensionHandler.class);
    }

}
