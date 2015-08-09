
package com.wakacommerce.openadmin.server.service.persistence.validation;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.FilterAndSortCriteria;
import com.wakacommerce.openadmin.server.domain.PersistencePackageRequest;
import com.wakacommerce.openadmin.server.service.AdminEntityService;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceResponse;


/**
 *
 * @ hui
 */
@Component("blUniqueValueValidator")
public class UniqueValueValidator implements PropertyValidator {
    
    @Resource(name = "blAdminEntityService")
    protected AdminEntityService adminEntityService;
    
    @Override
    public PropertyValidationResult validate(Entity entity, Serializable instance, Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration, BasicFieldMetadata propertyMetadata, String propertyName, String value) {
        PersistencePackageRequest ppr = PersistencePackageRequest.standard()
                .withCeilingEntityClassname(entity.getType()[0])
                .withFilterAndSortCriteria(new FilterAndSortCriteria[]{
                        new FilterAndSortCriteria(propertyName, value)
                });
        try {
            PersistenceResponse response = adminEntityService.getRecords(ppr);
            if(response.getDynamicResultSet().getTotalRecords() == 0) {
                return new PropertyValidationResult(true);
            } else {
                return new PropertyValidationResult(false, entity.getType()[0] + " with this value for attribute " + propertyName + " already exists. This attribute's value must be unique.");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new PropertyValidationResult(false, e.getMessage());
        }
    }

}
