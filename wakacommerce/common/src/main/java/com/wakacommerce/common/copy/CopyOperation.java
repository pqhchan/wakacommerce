
package com.wakacommerce.common.copy;

/**
 *Jeff Fischer
 */
public interface CopyOperation<T, G extends Exception> {

    T execute(T original) throws G;

}
