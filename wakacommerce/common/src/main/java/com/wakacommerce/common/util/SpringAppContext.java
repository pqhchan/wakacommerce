
package com.wakacommerce.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @ hui
 */
public class SpringAppContext implements ApplicationContextAware {
    
    private static ApplicationContext appContext;

    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.appContext = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
}
