
package com.wakacommerce.common.exception;

/**
 *
 * @ hui
 */
public class SiteNotFoundException extends RuntimeException {

    public SiteNotFoundException() {
        //do nothing
    }

    public SiteNotFoundException(Throwable cause) {
        super(cause);
    }

    public SiteNotFoundException(String message) {
        super(message);
    }

    public SiteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
