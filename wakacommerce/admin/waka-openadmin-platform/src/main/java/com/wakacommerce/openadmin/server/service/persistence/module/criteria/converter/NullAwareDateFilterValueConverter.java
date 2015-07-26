
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Jeff Fischer
 */
@Component("blNullAwareDateFilterValueConverter")
public class NullAwareDateFilterValueConverter implements FilterValueConverter<Date> {

    @Override
    public Date convert(String stringValue) {
        return parseDate(stringValue, FormatUtil.getDateFormat());
    }

    public Date parseDate(String value, SimpleDateFormat dateFormat) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException("Error while converting '" + value + "' into Date using pattern " + dateFormat.toPattern(), e);
        }
    }
}
