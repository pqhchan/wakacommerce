
package com.wakacommerce.common.util.tenant;

/**
 * 
 */
public interface IdentityOperation<T, G extends Throwable> {

    T execute() throws G;

}
