
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.util.BLCSystemProperty;

/**
 *
 * @ hui
 */
@Component("blStringLikeFilterValueConverter")
public class StringLikeFilterValueConverter implements FilterValueConverter<String> {
    
    protected boolean getOnlyStartsWith() {
        return BLCSystemProperty.resolveBooleanSystemProperty("admin.search.string.onlyStartsWith");
    }

    @Override
    public String convert(String stringValue) {
        return getOnlyStartsWith() ? stringValue.toLowerCase() + "%" : "%" + stringValue.toLowerCase() + "%";
    }

}
