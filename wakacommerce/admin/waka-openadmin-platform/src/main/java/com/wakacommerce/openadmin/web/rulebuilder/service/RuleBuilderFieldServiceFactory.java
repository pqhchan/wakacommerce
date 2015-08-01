
package com.wakacommerce.openadmin.web.rulebuilder.service;

import java.util.List;

/**
 * Factory class that returns the appropriate RuleBuilderFieldService
 * given the service name. The services are injected into the factory defined in applicationContext-servlet-open-admin.xml
 * @see com.wakacommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService
 *
 * 
 */
public interface RuleBuilderFieldServiceFactory {

    RuleBuilderFieldService createInstance(String name);

    List<RuleBuilderFieldService> getFieldServices();

    void setFieldServices(List<RuleBuilderFieldService> fieldServices);
}
