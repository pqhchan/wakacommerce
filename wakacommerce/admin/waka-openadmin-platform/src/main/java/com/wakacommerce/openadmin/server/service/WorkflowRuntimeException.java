
package com.wakacommerce.openadmin.server.service;

/**
 *
 * @ hui
 */
public class WorkflowRuntimeException extends RuntimeException {

    public WorkflowRuntimeException() {
        //do nothing
    }

    public WorkflowRuntimeException(Throwable cause) {
        super(cause);
    }

    public WorkflowRuntimeException(String message) {
        super(message);
    }

    public WorkflowRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
