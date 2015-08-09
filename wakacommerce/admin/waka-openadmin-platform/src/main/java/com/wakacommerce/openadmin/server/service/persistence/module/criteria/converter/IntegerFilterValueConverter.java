
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.springframework.stereotype.Component;

/**
 *
 * @ hui
 */
@Component("blIntegerFilterValueConverter")
public class IntegerFilterValueConverter implements FilterValueConverter<Integer> {

    @Override
    public Integer convert(String stringValue) {
        return Integer.valueOf(stringValue);
    }
}
