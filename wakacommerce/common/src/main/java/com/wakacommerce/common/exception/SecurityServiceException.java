
package com.wakacommerce.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @ hui
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Access is denied")
public class SecurityServiceException extends ServiceException {

    public SecurityServiceException() {
        super();
    }

    public SecurityServiceException(Throwable cause) {
        super(cause);
    }

    public SecurityServiceException(String message) {
        super(message);
    }

    public SecurityServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
