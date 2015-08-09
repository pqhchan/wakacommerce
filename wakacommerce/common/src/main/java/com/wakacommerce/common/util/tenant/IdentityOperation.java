
package com.wakacommerce.common.util.tenant;

/**
 *
 * @ hui
 */
public interface IdentityOperation<T, G extends Throwable> {

    T execute() throws G;

}
