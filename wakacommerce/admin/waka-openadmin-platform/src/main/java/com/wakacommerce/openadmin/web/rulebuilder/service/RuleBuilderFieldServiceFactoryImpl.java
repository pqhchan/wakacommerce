
package com.wakacommerce.openadmin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @ hui
 */
@Service("blRuleBuilderFieldServiceFactory")
public class RuleBuilderFieldServiceFactoryImpl implements RuleBuilderFieldServiceFactory {

    @Resource(name="blRuleBuilderFieldServices")
    protected List<RuleBuilderFieldService> fieldServices;

    @Override
    public RuleBuilderFieldService createInstance(String name) {

        for (RuleBuilderFieldService service : fieldServices) {
            if (service.getName().equals(name)){
                try {
                    return service.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    @Override
    public List<RuleBuilderFieldService> getFieldServices() {
        return fieldServices;
    }

    @Override
    public void setFieldServices(List<RuleBuilderFieldService> fieldServices) {
        this.fieldServices = fieldServices;
    }
}
