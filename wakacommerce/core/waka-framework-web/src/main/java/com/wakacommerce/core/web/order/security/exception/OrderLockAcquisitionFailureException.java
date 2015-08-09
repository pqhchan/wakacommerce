
package com.wakacommerce.core.web.order.security.exception;


public class OrderLockAcquisitionFailureException extends RuntimeException {

    private static final long serialVersionUID = -7826614694387239003L;

    protected OrderLockAcquisitionFailureException() {
        super();
    }
    
    public OrderLockAcquisitionFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderLockAcquisitionFailureException(String message) {
        super(message);
    }
    
    public OrderLockAcquisitionFailureException(Throwable cause) {
        super(cause);
    }

}
