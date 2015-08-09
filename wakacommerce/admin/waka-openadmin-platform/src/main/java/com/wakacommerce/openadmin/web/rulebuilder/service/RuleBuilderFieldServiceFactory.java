
package com.wakacommerce.openadmin.web.rulebuilder.service;

import java.util.List;

/**
 *
 * @ hui
 */
public interface RuleBuilderFieldServiceFactory {

    RuleBuilderFieldService createInstance(String name);

    List<RuleBuilderFieldService> getFieldServices();

    void setFieldServices(List<RuleBuilderFieldService> fieldServices);
}
