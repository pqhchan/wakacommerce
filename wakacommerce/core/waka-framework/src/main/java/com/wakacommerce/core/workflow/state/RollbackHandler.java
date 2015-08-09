
package com.wakacommerce.core.workflow.state;

import java.util.Map;

import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 *
 * @ hui
 */
public interface RollbackHandler<T> {

    public void rollbackState(Activity<? extends ProcessContext<T>> activity,
            ProcessContext<T> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException;

}
