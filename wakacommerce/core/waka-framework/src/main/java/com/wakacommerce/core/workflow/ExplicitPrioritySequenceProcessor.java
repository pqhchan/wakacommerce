
package com.wakacommerce.core.workflow;


/**
 * 
 */
public class ExplicitPrioritySequenceProcessor extends SequenceProcessor {
    
    // TODO: Factor in priority for activity order
    public ProcessContext doActivities(Object seedData) throws WorkflowException {
        return super.doActivities(seedData);
    }


}
