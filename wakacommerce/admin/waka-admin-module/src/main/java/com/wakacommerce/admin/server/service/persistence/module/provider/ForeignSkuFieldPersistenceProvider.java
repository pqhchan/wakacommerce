package com.wakacommerce.admin.server.service.persistence.module.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuImpl;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProviderAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

@Scope("prototype")
@Component("blForeignSkuFieldPersistenceProvider")
public class ForeignSkuFieldPersistenceProvider extends FieldPersistenceProviderAdapter {
    
    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) {
        if (!canHandleExtraction(extractValueRequest, property)) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        try {
            String val = extractValueRequest.getFieldManager().getFieldValue(extractValueRequest.getRequestedValue(), 
                    extractValueRequest.getMetadata().getForeignKeyProperty()).toString();
            String displayVal = null;
            
            if (!StringUtils.isEmpty(extractValueRequest.getMetadata().getForeignKeyDisplayValueProperty())) {
                String nameProperty = extractValueRequest.getMetadata().getForeignKeyDisplayValueProperty();
                Sku sku = (Sku) extractValueRequest.getRequestedValue();
                displayVal = extractValueRequest.getRecordHelper().getStringValueFromGetter(sku, nameProperty);
            }
            
            extractValueRequest.setDisplayVal(displayVal);
            
            property.setValue(val);
            property.setDisplayValue(displayVal);
        } catch (Exception e) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        return FieldProviderResponse.HANDLED_BREAK;
    }
    
    protected boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property) {
        String fkc = extractValueRequest.getMetadata().getForeignKeyClass();
        String rvc = null;
        if (extractValueRequest.getRequestedValue() != null) {
            rvc = extractValueRequest.getRequestedValue().getClass().getName();
        }
        
        return (SkuImpl.class.getName().equals(fkc) || Sku.class.getName().equals(fkc)) &&
                (SkuImpl.class.getName().equals(rvc) || Sku.class.getName().equals(rvc)) &&
                extractValueRequest.getMetadata().getFieldType().equals(SupportedFieldType.ADDITIONAL_FOREIGN_KEY);
    }

}
