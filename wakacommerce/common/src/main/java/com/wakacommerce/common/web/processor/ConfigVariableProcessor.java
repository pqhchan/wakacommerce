
package com.wakacommerce.common.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.common.web.expression.PropertiesVariableExpression;


/**
 *
 * @ hui
 */
public class ConfigVariableProcessor extends AbstractModelVariableModifierProcessor {

    public ConfigVariableProcessor() {
        super("config");
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        String resultVar = element.getAttributeValue("resultVar");
        if (resultVar == null) {
            resultVar = "value";
        }
        
        String attributeName = element.getAttributeValue("name");
        String attributeValue = BLCSystemProperty.resolveSystemProperty(attributeName);
        
        addToModel(arguments, resultVar, attributeValue);
    }
}
