
package com.wakacommerce.core.checkout.service.exception;

import com.wakacommerce.common.exception.BroadleafException;
import com.wakacommerce.core.checkout.service.workflow.CheckoutResponse;
import com.wakacommerce.core.checkout.service.workflow.CheckoutSeed;

public class CheckoutException extends BroadleafException {

    private static final long serialVersionUID = 1L;
    
    private CheckoutResponse checkoutResponse;

    public CheckoutException() {
        super();
    }

    public CheckoutException(String message, CheckoutSeed seed) {
        super(message);
        checkoutResponse = seed;
    }

    public CheckoutException(Throwable cause, CheckoutSeed seed) {
        super(cause);
        checkoutResponse = seed;
    }

    public CheckoutException(String message, Throwable cause, CheckoutSeed seed) {
        super(message, cause);
        checkoutResponse = seed;
    }

    public CheckoutResponse getCheckoutResponse() {
        return checkoutResponse;
    }

    public void setCheckoutResponse(CheckoutResponse checkoutResponse) {
        this.checkoutResponse = checkoutResponse;
    }

}
