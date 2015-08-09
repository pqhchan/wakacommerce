
package com.wakacommerce.admin.server.service.persistence.module.provider;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuImpl;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldManager;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProviderAdapter;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;


/**
 *
 * @ hui
 */
@Scope("prototype")
@Component("blSkuFieldsPersistenceProvider")
public class SkuFieldsPersistenceProvider extends FieldPersistenceProviderAdapter {

    
    @Override
    public int getOrder() {
        return SkuPricingPersistenceProvider.ORDER + 1;
    }
    
    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) {
        if (!canHandleExtraction(extractValueRequest, property)) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        Object actualValue = extractValueRequest.getRequestedValue();
        
        String value = extractValueRequest.getRecordHelper().formatValue(actualValue);
        String displayValue = value;
        if (displayValue == null) {
            try {
                displayValue = extractValueRequest.getRecordHelper().getStringValueFromGetter(extractValueRequest.getEntity(), property.getName());
                ((BasicFieldMetadata)property.getMetadata()).setDerived(true);
            } catch (Exception e) {
                //swallow all exceptions because null is fine for the display value
            }
        }
        
        property.setValue(value);
        property.setDisplayValue(displayValue);
        
        return FieldProviderResponse.HANDLED;
    }
    
    protected boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property) {
        return (
                extractValueRequest.getMetadata().getTargetClass().equals(SkuImpl.class.getName()) ||
                extractValueRequest.getMetadata().getTargetClass().equals(Sku.class.getName())
               ) 
                && !property.getName().contains(FieldManager.MAPFIELDSEPARATOR);
    }
    
}
