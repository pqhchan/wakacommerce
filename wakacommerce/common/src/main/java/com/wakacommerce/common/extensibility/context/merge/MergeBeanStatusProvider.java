
package com.wakacommerce.common.extensibility.context.merge;

import org.springframework.context.ApplicationContext;


public interface MergeBeanStatusProvider {

    public boolean isProcessingEnabled(Object bean, String beanName, ApplicationContext appCtx);

}
