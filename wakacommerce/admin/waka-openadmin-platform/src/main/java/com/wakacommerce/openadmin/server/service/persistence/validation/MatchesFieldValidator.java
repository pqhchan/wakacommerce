
package com.wakacommerce.openadmin.server.service.persistence.validation;

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
@Component("blMatchesFieldValidator")
public class MatchesFieldValidator extends ValidationConfigurationBasedPropertyValidator implements FieldNamePropertyValidator {

    @Override
    public boolean validateInternal(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        String otherField = validationConfiguration.get("otherField");
        return StringUtils.equals(entity.getPMap().get(otherField).getValue(), value);
    }

}
