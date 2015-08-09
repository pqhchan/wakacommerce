
package com.wakacommerce.common.util;

/**
 *
 * @ hui
 */
public class UnknownUnwrapTypeException extends RuntimeException {

    public UnknownUnwrapTypeException(Class unwrapType) {
        super( "Cannot unwrap to requested type [" + unwrapType.getName() + "]" );
    }

    public UnknownUnwrapTypeException(Class unwrapType, Throwable root) {
        this( unwrapType );
        super.initCause( root );
    }

}
