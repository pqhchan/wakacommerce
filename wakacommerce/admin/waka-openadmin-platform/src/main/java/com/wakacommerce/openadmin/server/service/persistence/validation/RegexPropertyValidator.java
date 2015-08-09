
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.ConfigurationItem;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.PatternSyntaxException;


/**
 *
 * @ hui
 */
@Component("blRegexPropertyValidator")
public class RegexPropertyValidator extends ValidationConfigurationBasedPropertyValidator {

    protected static final Log LOG = LogFactory.getLog(RegexPropertyValidator.class);

    protected boolean succeedForNullValues = true;

    protected boolean suceedForInvalidRegex = true;
    
    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        String expression = validationConfiguration.get("regularExpression");
        
        if (value == null) {
            return new PropertyValidationResult(succeedForNullValues, validationConfiguration.get(ConfigurationItem.ERROR_MESSAGE));
        }
        
        try {
            return new PropertyValidationResult(value.matches(expression), validationConfiguration.get(ConfigurationItem.ERROR_MESSAGE));
        } catch (PatternSyntaxException e) {
            String message = "Invalid regular expression pattern '" + expression + "' for " + propertyName;
            LOG.error(message, e);
            return new PropertyValidationResult(suceedForInvalidRegex, "Invalid regular expression pattern for " + propertyName);
        }
    }
    
    public boolean isSucceedForNullValues() {
        return succeedForNullValues;
    }

    public void setSucceedForNullValues(boolean succeedForNullValues) {
        this.succeedForNullValues = succeedForNullValues;
    }

    public boolean isSuceedForInvalidRegex() {
        return suceedForInvalidRegex;
    }

    public void setSuceedForInvalidRegex(boolean suceedForInvalidRegex) {
        this.suceedForInvalidRegex = suceedForInvalidRegex;
    }

}
