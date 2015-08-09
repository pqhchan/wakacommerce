
package com.wakacommerce.core.workflow.state;

import java.util.Map;

import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 *
 * @ hui
 */
public interface ActivityStateManager {

    public void registerState(RollbackHandler rollbackHandler, Map<String, Object> stateItems);

    public void registerState(Activity<? extends ProcessContext> activity, ProcessContext processContext, RollbackHandler rollbackHandler, Map<String, Object> stateItems);

    public void registerState(Activity<? extends ProcessContext> activity, ProcessContext processContext, String region, RollbackHandler rollbackHandler, Map<String, Object> stateItems);

    public void rollbackAllState() throws RollbackFailureException;

    public void rollbackRegionState(String region) throws RollbackFailureException;

    public void clearAllState();

    public void clearRegionState(String region);
}
