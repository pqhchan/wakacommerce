
package com.wakacommerce.common.vendor.service.exception;

import com.wakacommerce.common.vendor.service.message.FulfillmentPriceExceptionResponse;

public class FulfillmentPriceException extends Exception {

    private static final long serialVersionUID = 1L;

    protected FulfillmentPriceExceptionResponse fulfillmentPriceExceptionResponse;

    public FulfillmentPriceException() {
        super();
    }

    public FulfillmentPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FulfillmentPriceException(String message) {
        super(message);
    }

    public FulfillmentPriceException(Throwable cause) {
        super(cause);
    }

    public FulfillmentPriceExceptionResponse getFulfillmentPriceExceptionResponse() {
        return fulfillmentPriceExceptionResponse;
    }

    public void setFulfillmentPriceExceptionResponse(FulfillmentPriceExceptionResponse fulfillmentPriceExceptionResponse) {
        this.fulfillmentPriceExceptionResponse = fulfillmentPriceExceptionResponse;
    }
}
