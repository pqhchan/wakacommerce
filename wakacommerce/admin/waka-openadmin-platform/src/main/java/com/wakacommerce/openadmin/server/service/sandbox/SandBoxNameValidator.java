  
package com.wakacommerce.openadmin.server.service.sandbox;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.sandbox.service.SandBoxService;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidationResult;
import com.wakacommerce.openadmin.server.service.persistence.validation.ValidationConfigurationBasedPropertyValidator;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Ensures that the SandBox name is unique within a given site.
 * 
 * 
 */
@Component("blSandBoxNameValidator")
public class SandBoxNameValidator extends ValidationConfigurationBasedPropertyValidator {

    @Resource(name = "blSandBoxService")
    protected SandBoxService sandboxService;

    /**
     * Denotes what should occur when this validator encounters a null value to validate against. Default behavior is to
     * allow them, which means that this validator will always return true with null values
     */
    protected boolean succeedForNullValues = false;

    protected String ERROR_DUPLICATE_SANDBOX_NAME = "errorDuplicateSandBoxName";

    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {

        if (value == null) {
            return new PropertyValidationResult(succeedForNullValues);
        }

        Property theProp = entity.getPMap().get(propertyName);
        if (theProp != null && theProp.getIsDirty()) {
            if (!sandboxService.checkForExistingApprovalSandboxWithName(value)) {
                return new PropertyValidationResult(false, ERROR_DUPLICATE_SANDBOX_NAME);
            }
        }

        return new PropertyValidationResult(true);
    }

    public boolean isSucceedForNullValues() {
        return succeedForNullValues;
    }

    public void setSucceedForNullValues(boolean succeedForNullValues) {
        this.succeedForNullValues = succeedForNullValues;
    }

}
