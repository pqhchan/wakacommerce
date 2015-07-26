
package com.wakacommerce.common.util.tenant;

/**
 *Jeff Fischer
 */
public interface IdentityOperation<T, G extends Throwable> {

    T execute() throws G;

}
