
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.converter;

/**
 * 
 */
public interface FilterValueConverter<T> {

    T convert(String stringValue);

}
