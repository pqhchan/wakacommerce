package com.wakacommerce.openadmin.web.form.entity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.server.service.AdminEntityService;
import com.wakacommerce.openadmin.server.service.JSCompatibilityHelper;

import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
@Component("blEntityFormValidator")
public class EntityFormValidator {

    public boolean validate(EntityForm form, Map<String, List<String>> propertyErrors, Errors errors) {
        return validate(form, propertyErrors, null, errors);
    }

    public boolean validate(EntityForm form, Map<String, List<String>> propertyErrors, List<String> globalErrors, Errors errors) {
        if (MapUtils.isEmpty(propertyErrors) && CollectionUtils.isEmpty(globalErrors)) {
            return true;
        }
        
        if (MapUtils.isNotEmpty(propertyErrors)) {
            for (Map.Entry<String, List<String>> pe : propertyErrors.entrySet()) {
                for (String errorMessage : pe.getValue()) {
                    String unserializedFieldName = pe.getKey();
                    String serializedFieldName = JSCompatibilityHelper.encode(unserializedFieldName);

                    Field field = null;
                    if (StringUtils.contains(unserializedFieldName, DynamicEntityFormInfo.FIELD_SEPARATOR)) {
                        String[] fieldInfo = unserializedFieldName.split("\\" + DynamicEntityFormInfo.FIELD_SEPARATOR);
                        field = form.getDynamicForm(fieldInfo[0]).getFields().get(fieldInfo[1]);
                    } else if (form.getFields().get(unserializedFieldName) != null) {
                        field = form.getFields().get(unserializedFieldName);
                    }
                    
                    //If the submitted field was a radio button but has validation associated with it, that radio field
                    //will have never been submitted in the POST and thus will not have ever been attached to the EntityForm.
                    //We still want to notate the fact that there was a validation failure on that field
                    String value = (field != null) ? field.getValue() : null;
                    
                    String[] errorCodes = ((AbstractBindingResult) errors).resolveMessageCodes(errorMessage, serializedFieldName);
                    FieldError fieldError = new FieldError("entityForm", String.format("fields[%s].value", serializedFieldName),
                            value, false, errorCodes, null, errorMessage);
                    ((AbstractBindingResult) errors).addError(fieldError);
                }
            }
        }
        
        if (CollectionUtils.isNotEmpty(globalErrors)) {
            for (String errorMessage : globalErrors) {
                errors.reject(errorMessage, errorMessage);
            }
        }
        
        return false;
    }

    public boolean validate(EntityForm form, Entity entity, Errors errors) {
        if (entity.isValidationFailure()) {
            return validate(form, entity.getPropertyValidationErrors(), entity.getGlobalValidationErrors(), errors);
        }
        
        return true;
    }

}
