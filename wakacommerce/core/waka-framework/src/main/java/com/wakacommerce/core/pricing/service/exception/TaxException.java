
package com.wakacommerce.core.pricing.service.exception;

/**
 *jfischer
 *
 */
public class TaxException extends Exception {

    /**
     * 
     */
    public TaxException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public TaxException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public TaxException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public TaxException(Throwable cause) {
        super(cause);
    }

}
