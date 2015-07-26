
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.springframework.stereotype.Component;

/**
 *Jeff Fischer
 */
@Component("blBooleanFilterValueConverter")
public class BooleanFilterValueConverter implements FilterValueConverter<Boolean> {

    @Override
    public Boolean convert(String stringValue) {
        return Boolean.valueOf(stringValue);
    }
}
