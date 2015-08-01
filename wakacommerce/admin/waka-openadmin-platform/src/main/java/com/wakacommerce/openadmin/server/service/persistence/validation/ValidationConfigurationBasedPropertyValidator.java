
package com.wakacommerce.openadmin.server.service.persistence.validation;

import com.wakacommerce.common.presentation.ConfigurationItem;
import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;


/**
 * Provides a default validate method that uses the validation configuration map to pull out the error key and pre-populate
 * the {@link PropertyValidationResult} based on {@link ConfigurationItem#ERROR_MESSAGE}.
 * 
 * This class should be used as your base if you are writing a validator based on a {@link ValidationConfiguration}
 *
 *     
 */
public abstract class ValidationConfigurationBasedPropertyValidator implements PropertyValidator {

    @Override
    public PropertyValidationResult validate(Entity entity, Serializable instance, Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        return new PropertyValidationResult(validateInternal(entity,
                instance,
                entityFieldMetadata,
                validationConfiguration,
                propertyMetadata,
                propertyName,
                value), validationConfiguration.get(ConfigurationItem.ERROR_MESSAGE));
    }
    
    /**
     * Delegate method for {@link ValidationConfiguration}-based processors that don't need to return an error message
     */
    public boolean validateInternal(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        return false;
    }

    
}
