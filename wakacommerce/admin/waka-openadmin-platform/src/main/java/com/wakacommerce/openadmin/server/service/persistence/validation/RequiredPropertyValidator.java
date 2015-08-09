
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * @ hui
 */
@Component("blRequiredPropertyValidator")
public class RequiredPropertyValidator implements GlobalPropertyValidator {

    public static String ERROR_MESSAGE = "requiredValidationFailure";
    
    @Override
    public PropertyValidationResult validate(Entity entity,
                            Serializable instance,
                            Map<String, FieldMetadata> entityFieldMetadata,
                            BasicFieldMetadata propertyMetadata,
                            String propertyName,
                            String value) {
        boolean required = BooleanUtils.isTrue(propertyMetadata.getRequired());
        if (propertyMetadata.getRequiredOverride() != null) {
            required = propertyMetadata.getRequiredOverride();
        }
        boolean valid = !(required && StringUtils.isEmpty(value));
        return new PropertyValidationResult(valid, ERROR_MESSAGE);
    }

}
