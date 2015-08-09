package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @ hui
 */
public class BeanValidationEntityValidatorServiceImpl extends EntityValidatorServiceImpl {

    @Autowired
    protected Validator validator;

    protected boolean useDefaultEntityValidations = true;
    
    @Override
    public void validate(Entity entity, Serializable instance, Map<String, FieldMetadata> mergedProperties,
            RecordHelper recordHelper, boolean validateUnsubmittedProperties) {        
        if (isUseDefaultEntityValidations()) {
            super.validate(entity, instance, mergedProperties, recordHelper, validateUnsubmittedProperties);
        }

        Set<ConstraintViolation<Serializable>> violations = getValidator().validate(instance);
        for (ConstraintViolation<Serializable> violation : violations) {
            entity.addValidationError(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isUseDefaultEntityValidations() {
        return useDefaultEntityValidations;
    }

    public void setUseDefaultEntityValidations(boolean useDefaultEntityValidations) {
        this.useDefaultEntityValidations = useDefaultEntityValidations;
    }

}
