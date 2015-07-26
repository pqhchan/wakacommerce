
package com.wakacommerce.core.workflow;

public interface ProcessContextFactory<U, T> {

    public ProcessContext<U> createContext(T preSeedData) throws WorkflowException;

}
