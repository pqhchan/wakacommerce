
package com.wakacommerce.common.logging;


/**
 * <p>LogManager class that adds support for retrieving a specialized
 * Logger instance (SupportLogger). SupportLogger provides support for the
 * SUPPORT log level.</p>
 *
 * 
 *  
 */
public class SupportLogManager {

    /**
     * Retrieve a SupportLogger instance
     *
     * @param moduleName The name of the module - will appear in the log message
     * @param name The name for the logger - will appear in the log message
     * @return the specialized Logger instance supporting the SUPPORT log level
     */
    public static SupportLogger getLogger(final String moduleName, String name) {
        return new SupportLogger(moduleName, name);
    }

    /**
     * Retrieve a SupportLogger instance
     *
     * @param moduleName The name of the module - will appear in the log message
     * @param clazz The class from which the logging is being called - will appear in the log message
     * @return the specialized Logger instance supporting the SUPPORT log level
     */
    public static SupportLogger getLogger(final String moduleName, Class<?> clazz) {
        return getLogger(moduleName, clazz.getSimpleName());
    }



}
