
package com.wakacommerce.common.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.common.web.expression.PropertiesVariableExpression;


/**
 * <p>
 * Looks up the value of a configuration variable and adds the value to the model.
 * 
 * <p>
 * While this adds the configuration value onto the model, you might want to use the value of this in larger expression. In
 * that instance you may want to use {@link PropertiesVariableExpression} instead with {@code #props.get('property')}.
 * 
 * @parameter name (required) the name of the system property to look up
 * @parameter resultVar (optional) what model variable the system property value is added to, defaults to <b>value</b>
 * 
 * 
 * @see {@link PropertiesVariableExpression}
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
