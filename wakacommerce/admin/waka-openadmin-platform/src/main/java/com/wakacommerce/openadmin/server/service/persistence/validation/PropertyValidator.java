
package com.wakacommerce.openadmin.server.service.persistence.validation;

import com.wakacommerce.common.presentation.ConfigurationItem;
import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * @ hui
 */
public interface PropertyValidator {

    public PropertyValidationResult validate(Entity entity,
                                            Serializable instance,
                                            Map<String, FieldMetadata> entityFieldMetadata,
                                            Map<String, String> validationConfiguration,
                                            BasicFieldMetadata propertyMetadata,
                                            String propertyName,
                                            String value);

}
