
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceException;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

/**
 *Jeff Fischer
 */
@Component("blDefaultFieldPersistenceProvider")
@Scope("prototype")
public class DefaultFieldPersistenceProvider extends FieldPersistenceProviderAdapter {

    @Override
    public FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest, Serializable instance) throws PersistenceException {
        boolean dirty;
        try {
            Property p = populateValueRequest.getProperty();
            Object value = populateValueRequest.getFieldManager().getFieldValue(instance, p.getName());

            if (value != null) {
                p.setOriginalValue(String.valueOf(value));
                p.setOriginalDisplayValue(p.getOriginalValue());
            }

            dirty = checkDirtyState(populateValueRequest, instance, populateValueRequest.getRequestedValue());
            populateValueRequest.getFieldManager().setFieldValue(instance,
                    populateValueRequest.getProperty().getName(), populateValueRequest.getRequestedValue());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        populateValueRequest.getProperty().setIsDirty(dirty);
        return FieldProviderResponse.HANDLED;
    }

    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) throws PersistenceException {
        if (extractValueRequest.getRequestedValue() != null) {
            String val = extractValueRequest.getRequestedValue().toString();
            property.setValue(val);
            property.setDisplayValue(extractValueRequest.getDisplayVal());
        }
        return FieldProviderResponse.HANDLED;
    }

}
