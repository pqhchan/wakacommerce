
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.springframework.stereotype.Component;

/**
 *Jeff Fischer
 */
@Component("blLongFilterValueConverter")
public class LongFilterValueConverter implements FilterValueConverter<Long> {

    @Override
    public Long convert(String stringValue) {
        return Long.valueOf(stringValue);
    }
}
