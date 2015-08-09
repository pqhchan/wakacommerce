
package com.wakacommerce.openadmin.server.service.persistence.validation;

import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.service.persistence.module.BasicPersistenceModule;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


/**
 *
 * @ hui
 */
public interface EntityValidatorService {

    public void validate(Entity submittedEntity, @Nullable Serializable instance, Map<String, FieldMetadata> propertiesMetadata, 
            RecordHelper recordHelper, boolean validateUnsubmittedProperties);

    public List<GlobalPropertyValidator> getGlobalEntityValidators();

    public void setGlobalEntityValidators(List<GlobalPropertyValidator> globalEntityValidators);

}
