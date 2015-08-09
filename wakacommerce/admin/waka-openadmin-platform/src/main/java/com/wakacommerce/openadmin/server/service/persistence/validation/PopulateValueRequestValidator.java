
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.springframework.core.Ordered;

import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.server.service.persistence.module.BasicPersistenceModule;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;

import java.io.Serializable;


/**
 *
 * @ hui
 */
public interface PopulateValueRequestValidator extends Ordered {

    public PropertyValidationResult validate(PopulateValueRequest populateValueRequest, Serializable instance);
    
}
