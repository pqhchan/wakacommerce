
package com.wakacommerce.openadmin.server.service;

import org.apache.commons.lang3.StringUtils;

/**
 *Jeff Fischer
 */
public class JSCompatibilityHelper {

    public static String encode(String name) {
        return StringUtils.isEmpty(name)?name:name.replace(".", "__");
    }

    public static String unencode(String name) {
        return StringUtils.isEmpty(name)?name:name.replace("__", ".");
    }

}
