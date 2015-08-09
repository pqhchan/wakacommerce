
package com.wakacommerce.common.locale.util;

import com.wakacommerce.common.locale.domain.Locale;

/**
 *
 * @ hui
 */
public final class LocaleUtil {

    public static String findLanguageCode(Locale locale) {
        if (locale != null && locale.getLocaleCode() != null && locale.getLocaleCode().indexOf("_") > 0) {
            int endIndex = locale.getLocaleCode().indexOf("_");
            char[] localeCodeChars = locale.getLocaleCode().toCharArray();
            StringBuffer sb = new StringBuffer();
            for(int i=0; i < endIndex; i++){
                sb.append(localeCodeChars[i]);
            }
            return sb.toString();
        }
        return null;
    }

}
