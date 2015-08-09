
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.AddFilterPropertiesRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.AddSearchMappingRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public class FieldPersistenceProviderAdapter extends AbstractFieldPersistenceProvider {

    @Override
    public FieldProviderResponse addSearchMapping(AddSearchMappingRequest addSearchMappingRequest, List<FilterMapping> filterMappings) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest, Serializable instance) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse filterProperties(AddFilterPropertiesRequest addFilterPropertiesRequest, Map<String, FieldMetadata> properties) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    protected boolean checkDirtyState(PopulateValueRequest request, Object instance, Object checkValue) throws Exception {
        boolean dirty = !(instance == null && checkValue == null) && (instance == null || checkValue == null);
        if (!dirty) {
            Object value = request.getFieldManager().getFieldValue(instance, request.getProperty().getName());
            if (checkValue instanceof String) {
                checkValue = ((String) checkValue).trim();
            }
            if (value instanceof String) {
                value = ((String) value).trim();
            }
            if (value instanceof BigDecimal) {
                checkValue = ((BigDecimal) checkValue).setScale(((BigDecimal) value).scale(), RoundingMode.HALF_UP);
            }
            dirty = value == null || !value.equals(checkValue);
        }
        return dirty;
    }

    protected void setNonDisplayableValues(PopulateValueRequest request) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        MessageSource messages = context.getMessageSource();
        String label = "(" + messages.getMessage("Workflow_not_displayable", null, "Not Displayable", context.getJavaLocale()) + ")";
        request.getProperty().setDisplayValue(label);
        request.getProperty().setOriginalDisplayValue(label);
    }
}
