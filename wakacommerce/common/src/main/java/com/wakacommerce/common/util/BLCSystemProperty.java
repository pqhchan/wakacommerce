
package com.wakacommerce.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.config.service.SystemPropertiesService;

/**
 *
 * @ hui
 */
@Service("blBLCSystemProperty")
public class BLCSystemProperty implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;

    public static String resolveSystemProperty(String name) {
        return getSystemPropertiesService().resolveSystemProperty(name);
    }
    
    public static String resolveSystemProperty(String name, String defaultValue) {
        return getSystemPropertiesService().resolveSystemProperty(name, defaultValue);
    }

    public static int resolveIntSystemProperty(String name) {
        return getSystemPropertiesService().resolveIntSystemProperty(name);
    }
    
    public static int resolveIntSystemProperty(String name, int defaultValue) {
        return getSystemPropertiesService().resolveIntSystemProperty(name, defaultValue);
    }

    public static boolean resolveBooleanSystemProperty(String name) {
        return getSystemPropertiesService().resolveBooleanSystemProperty(name);
    }
    
    public static boolean resolveBooleanSystemProperty(String name, boolean defaultValue) {
        return getSystemPropertiesService().resolveBooleanSystemProperty(name, defaultValue);
    }

    public static long resolveLongSystemProperty(String name) {
        return getSystemPropertiesService().resolveLongSystemProperty(name);
    }
    
    public static long resolveLongSystemProperty(String name, long defaultValue) {
        return getSystemPropertiesService().resolveLongSystemProperty(name, defaultValue);
    }

    protected static SystemPropertiesService getSystemPropertiesService() {
        return (SystemPropertiesService) applicationContext.getBean("blSystemPropertiesService");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BLCSystemProperty.applicationContext = applicationContext;
    }

}
