
package com.wakacommerce.common.vendor.service.exception;

import com.wakacommerce.common.exception.BroadleafException;

public class PaymentException extends BroadleafException {

    private static final long serialVersionUID = 1L;

    public PaymentException() {
        super();
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Throwable cause) {
        super(cause);
    }

}
