
package com.wakacommerce.common.util;

/**
 *
 * @ hui
 */
public interface Wrappable {

    public boolean isUnwrappableAs(Class unwrapType);

    public <T> T unwrap(Class<T> unwrapType);

}
