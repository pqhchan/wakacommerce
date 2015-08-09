
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceException;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @ hui
 */
public abstract class AbstractMoneyFieldPersistenceProvider extends FieldPersistenceProviderAdapter {
    
    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) throws PersistenceException {
        if (!canHandleExtraction(extractValueRequest, property)) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        if (extractValueRequest.getRequestedValue() == null) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        property.setValue(formatValue((BigDecimal)extractValueRequest.getRequestedValue(), extractValueRequest, property));
        property.setDisplayValue(formatDisplayValue((BigDecimal)extractValueRequest.getRequestedValue(), extractValueRequest, property));
        
        return FieldProviderResponse.HANDLED_BREAK;
    }
    
    protected String formatValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
        NumberFormat format = NumberFormat.getInstance(getLocale(extractValueRequest, property));
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setGroupingUsed(false);
        return format.format(value);
    }
    
    protected String formatDisplayValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
        Locale locale = getLocale(extractValueRequest, property);
        Currency currency = getCurrency(extractValueRequest, property);
        return BroadleafCurrencyUtils.getNumberFormatFromCache(locale, currency).format(value);
    }
    
    protected abstract boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property);
    
    protected abstract Locale getLocale(ExtractValueRequest extractValueRequest, Property property);
    
    protected abstract Currency getCurrency(ExtractValueRequest extractValueRequest, Property property);
    
}
