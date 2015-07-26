
package com.wakacommerce.core.workflow;

import org.springframework.beans.factory.BeanNameAware;

public interface ErrorHandler extends BeanNameAware{

    public void handleError(ProcessContext context, Throwable th) throws WorkflowException;

}
