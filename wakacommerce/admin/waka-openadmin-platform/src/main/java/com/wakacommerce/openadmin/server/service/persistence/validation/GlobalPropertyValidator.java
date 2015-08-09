
package com.wakacommerce.openadmin.server.service.persistence.validation;

import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.service.persistence.module.BasicPersistenceModule;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * @ hui
 */
public interface GlobalPropertyValidator {

    public PropertyValidationResult validate(Entity entity,
                                            Serializable instance,
                                            Map<String, FieldMetadata> entityFieldMetadata,
                                            BasicFieldMetadata propertyMetadata,
                                            String propertyName,
                                            String value);

}
