
package com.wakacommerce.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @ hui
 */
@Component("blApplicationContextHolder")
public class ApplicationContextHolder implements ApplicationContextAware {
    
    protected static ApplicationContext context;
    
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
