
package com.wakacommerce.core.workflow;

import com.wakacommerce.common.exception.BroadleafException;

public class WorkflowException extends BroadleafException {

    private static final long serialVersionUID = 1L;

    public WorkflowException() {
        super();
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }

}
