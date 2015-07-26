
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.springframework.stereotype.Component;

/**
 *Jeff Fischer
 */
@Component("blStringFilterValueConverter")
public class StringFilterValueConverter implements FilterValueConverter<String>{

    @Override
    public String convert(String stringValue) {
        return stringValue;
    }
}
