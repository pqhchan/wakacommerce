
package com.wakacommerce.core.workflow;

import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.state.RollbackHandler;

/**
 *
 * @ hui
 */
public interface Activity<T extends ProcessContext<?>> extends BeanNameAware, Ordered {

    public T execute(T context) throws Exception;

    public boolean shouldExecute(T context);

    public ErrorHandler getErrorHandler();

    public void setErrorHandler(final ErrorHandler errorHandler);
    
    public String getBeanName();

    public RollbackHandler<T> getRollbackHandler();

    public void setRollbackHandler(RollbackHandler<T> rollbackHandler);

    public String getRollbackRegion();

    public void setRollbackRegion(String rollbackRegion);

    public Map<String, Object> getStateConfiguration();

    public void setStateConfiguration(Map<String, Object> stateConfiguration);

    public boolean getAutomaticallyRegisterRollbackHandler();

    public void setAutomaticallyRegisterRollbackHandler(boolean automaticallyRegisterRollbackHandler);
}
