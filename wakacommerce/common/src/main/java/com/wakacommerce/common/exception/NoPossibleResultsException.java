
package com.wakacommerce.common.exception;

/**
 *
 * @ hui
 */
public class NoPossibleResultsException extends RuntimeException {
    
    private static final long serialVersionUID = 2422275745139590462L;

    // for serialization purposes
    protected NoPossibleResultsException() {
        super();
    }
    
    public NoPossibleResultsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoPossibleResultsException(String message) {
        super(message);
    }
    
    public NoPossibleResultsException(Throwable cause) {
        super(cause);
    }

}
