
package com.wakacommerce.common.exception;

/**
 *
 * @ hui
 */
public class ProxyDetectionException extends RuntimeException {

    public ProxyDetectionException() {
    }

    public ProxyDetectionException(String message) {
        super(message);
    }

    public ProxyDetectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxyDetectionException(Throwable cause) {
        super(cause);
    }

    public ProxyDetectionException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
