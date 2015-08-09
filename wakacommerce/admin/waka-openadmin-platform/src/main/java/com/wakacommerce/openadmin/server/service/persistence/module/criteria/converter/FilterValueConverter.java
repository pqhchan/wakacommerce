
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

/**
 *
 * @ hui
 */
public interface FilterValueConverter<T> {

    T convert(String stringValue);

}
