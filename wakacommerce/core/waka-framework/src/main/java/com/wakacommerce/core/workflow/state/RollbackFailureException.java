
package com.wakacommerce.core.workflow.state;

import java.util.Map;

import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.WorkflowException;

/**
 *
 * @ hui
 */
public class RollbackFailureException extends WorkflowException {

    private static final long serialVersionUID = 1L;

    private Activity<? extends ProcessContext<?>> activity;
    private ProcessContext<?> processContext;
    private Map<String, Object> stateItems;

    public RollbackFailureException() {
    }

    public RollbackFailureException(Throwable cause) {
        super(cause);
    }

    public RollbackFailureException(String message) {
        super(message);
    }

    public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public Activity<? extends ProcessContext<?>> getActivity() {
        return activity;
    }

    public void setActivity(Activity<? extends ProcessContext<?>> activity) {
        this.activity = activity;
    }

    public ProcessContext<?> getProcessContext() {
        return processContext;
    }

    public void setProcessContext(ProcessContext<?> processContext) {
        this.processContext = processContext;
    }

    public Map<String, Object> getStateItems() {
        return stateItems;
    }

    public void setStateItems(Map<String, Object> stateItems) {
        this.stateItems = stateItems;
    }
}
