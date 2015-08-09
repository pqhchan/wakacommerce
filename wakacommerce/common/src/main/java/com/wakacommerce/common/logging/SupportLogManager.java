
package com.wakacommerce.common.logging;


/**
 *
 * @ hui
 */
public class SupportLogManager {

    public static SupportLogger getLogger(final String moduleName, String name) {
        return new SupportLogger(moduleName, name);
    }

    public static SupportLogger getLogger(final String moduleName, Class<?> clazz) {
        return getLogger(moduleName, clazz.getSimpleName());
    }



}
