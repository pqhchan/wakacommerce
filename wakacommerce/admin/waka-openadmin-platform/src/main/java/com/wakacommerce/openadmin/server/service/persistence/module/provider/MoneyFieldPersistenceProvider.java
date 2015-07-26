
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.currency.util.CurrencyCodeIdentifiable;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;

import java.util.Currency;
import java.util.Locale;

/**
 * Persistence provider capable of extracting friendly display values for Money fields
 * 
 *Andre Azzolini (apazzolini)
 */
@Scope("prototype")
@Component("blMoneyFieldPersistenceProvider")
public class MoneyFieldPersistenceProvider extends AbstractMoneyFieldPersistenceProvider {
    
    @Override
    public int getOrder() {
        return FieldPersistenceProvider.MONEY;
    }
    
    @Override
    protected boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property) {
        return extractValueRequest.getMetadata().getFieldType() == SupportedFieldType.MONEY;
    }
    
    @Override
    protected Locale getLocale(ExtractValueRequest extractValueRequest, Property property) {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        return brc.getJavaLocale();
    }

    @Override
    protected Currency getCurrency(ExtractValueRequest extractValueRequest, Property property) {
        String currencyCodeField = extractValueRequest.getMetadata().getCurrencyCodeField();
        if (!StringUtils.isEmpty(currencyCodeField)) {
            try {
                return Currency.getInstance((String) extractValueRequest.getFieldManager().getFieldValue(extractValueRequest.getEntity(), currencyCodeField));
            } catch (Exception e) {
                //do nothing
            }
        }
        if (extractValueRequest.getEntity() instanceof CurrencyCodeIdentifiable) {
            try {
                return Currency.getInstance(((CurrencyCodeIdentifiable) extractValueRequest.getEntity()).getCurrencyCode());
            } catch (Exception e) {
                //do nothing
            }
        }
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        return brc.getJavaCurrency();
    }
    
}
