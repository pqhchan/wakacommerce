
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.server.service.persistence.module.FieldNotAvailableException;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;


/**
 *
 * @ hui
 */
@Component("blBasicFieldTypeValidator")
public class BasicFieldTypeValidator implements PopulateValueRequestValidator {

    @Override
    public PropertyValidationResult validate(PopulateValueRequest populateValueRequest, Serializable instance) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        Locale locale = brc.getJavaLocale();
        DecimalFormat format = populateValueRequest.getDataFormatProvider().getDecimalFormatter();
        ParsePosition pp;
        switch(populateValueRequest.getMetadata().getFieldType()) {
            case INTEGER:
                try {
                    if (int.class.isAssignableFrom(populateValueRequest.getReturnType()) || Integer.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                        Integer.parseInt(populateValueRequest.getRequestedValue());
                    } else if (byte.class.isAssignableFrom(populateValueRequest.getReturnType()) || Byte.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                        Byte.parseByte(populateValueRequest.getRequestedValue());
                    } else if (short.class.isAssignableFrom(populateValueRequest.getReturnType()) || Short.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                        Short.parseShort(populateValueRequest.getRequestedValue());
                    } else if (long.class.isAssignableFrom(populateValueRequest.getReturnType()) || Long.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                        Long.parseLong(populateValueRequest.getRequestedValue());
                    }
                } catch (NumberFormatException e) {
                    return new PropertyValidationResult(false, "Field must be an valid number");
                }
                break;
            case DECIMAL:
                pp = new ParsePosition(0);
                if (BigDecimal.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                    format.setParseBigDecimal(true);
                    format.parse(populateValueRequest.getRequestedValue(), pp);
                    format.setParseBigDecimal(false);
                } else {
                    format.parse(populateValueRequest.getRequestedValue(), pp);
                }
                if (pp.getIndex() != populateValueRequest.getRequestedValue().length()) {
                    return new PropertyValidationResult(false, "Field must be a valid decimal");
                }
                break;
            case MONEY:
                pp = new ParsePosition(0);
                if (BigDecimal.class.isAssignableFrom(populateValueRequest.getReturnType()) || Money.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                    format.setParseBigDecimal(true);
                    format.parse(populateValueRequest.getRequestedValue(), pp);
                    format.setParseBigDecimal(false);
                } else if (Double.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                    format.parse(populateValueRequest.getRequestedValue(), pp);
                }
                if (pp.getIndex() != populateValueRequest.getRequestedValue().length()) {
                    return new PropertyValidationResult(false, "Field must be a valid number");
                }
                break;
            case DATE:
                try {
                    populateValueRequest.getDataFormatProvider().getSimpleDateFormatter().parse(populateValueRequest.getRequestedValue());
                } catch (ParseException e) {
                    return new PropertyValidationResult(false, "Field must be a date of the format: " + populateValueRequest.getDataFormatProvider().getSimpleDateFormatter().toPattern());
                }
                break;
            case FOREIGN_KEY:
            case ADDITIONAL_FOREIGN_KEY:
                if (Collection.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                    Collection collection;
                    try {
                        collection = (Collection) populateValueRequest.getFieldManager().getFieldValue(instance, populateValueRequest.getProperty().getName());
                    } catch (FieldNotAvailableException e) {
                        return new PropertyValidationResult(false, "External entity cannot be added to the specified collection at " + populateValueRequest.getProperty().getName());
                    } catch (IllegalAccessException e) {
                        return new PropertyValidationResult(false, "External entity cannot be added to the specified collection at " + populateValueRequest.getProperty().getName());
                    }
                } else if (Map.class.isAssignableFrom(populateValueRequest.getReturnType())) {
                    return new PropertyValidationResult(false, "External entity cannot be added to a map at " + populateValueRequest.getProperty().getName());
                }
            case ID:
                if (populateValueRequest.getSetId()) {
                    switch (populateValueRequest.getMetadata().getSecondaryType()) {
                        case INTEGER:
                            Long.valueOf(populateValueRequest.getRequestedValue());
                            break;
                        default:
                            //do nothing
                    }
                }
            default:
                return new PropertyValidationResult(true);
        }
        return new PropertyValidationResult(true);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1000;
    }
}
