
package com.wakacommerce.openadmin.web.processor;

import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldWrapper;
import com.wakacommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService;
import com.wakacommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldServiceFactory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 *  
 */
@Component("blAdminFieldBuilderProcessor")
public class AdminFieldBuilderProcessor extends AbstractLocalVariableDefinitionElementProcessor {

    @Resource(name = "blRuleBuilderFieldServiceFactory")
    protected RuleBuilderFieldServiceFactory ruleBuilderFieldServiceFactory;

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     */
    public AdminFieldBuilderProcessor() {
        super("admin_field_builder");
    }

    @Override
    public int getPrecedence() {
        return 100;
    }

    @Override
    protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
        FieldWrapper fieldWrapper = new FieldWrapper();
        
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("fieldBuilder"));
        String fieldBuilder = (String) expression.execute(arguments.getConfiguration(), arguments);

        if (fieldBuilder != null) {
            RuleBuilderFieldService ruleBuilderFieldService = ruleBuilderFieldServiceFactory.createInstance(fieldBuilder);
            if (ruleBuilderFieldService != null) {
                fieldWrapper = ruleBuilderFieldService.buildFields();
            }
        }
        
        Map<String, Object> newVars = new HashMap<String, Object>();
        newVars.put("fieldWrapper", fieldWrapper);
        return newVars;
    }

    @Override
    protected boolean removeHostElement(Arguments arguments, Element element) {
        return false;
    }
}
