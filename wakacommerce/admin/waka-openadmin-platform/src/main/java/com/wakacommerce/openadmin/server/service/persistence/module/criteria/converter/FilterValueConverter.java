
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

/**
 *Jeff Fischer
 */
public interface FilterValueConverter<T> {

    T convert(String stringValue);

}
