
package com.wakacommerce.common.copy;

/**
 *
 * @ hui
 */
public interface CopyOperation<T, G extends Exception> {

    T execute(T original) throws G;

}
