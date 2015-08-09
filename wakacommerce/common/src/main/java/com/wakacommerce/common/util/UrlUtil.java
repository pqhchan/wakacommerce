
package com.wakacommerce.common.util;

import org.apache.commons.lang.StringUtils;

public class UrlUtil {
    public static String generateUrlKey(String toConvert) {
        if (toConvert != null) {
            toConvert = toConvert.replaceAll(" ", "-");
            if (toConvert.matches(".*?\\W.*?")) {
                //remove all non-word characters
                String result = toConvert.replaceAll("[^\\w-]+", "");
                //uncapitalizes the first letter of the url key
                return StringUtils.uncapitalize(result);
            } else {
                return StringUtils.uncapitalize(toConvert);
            }
        }
        return toConvert;
    }

        public static String fixRedirectUrl(String contextPath, String url) {
            if (url.indexOf("//") < 0) {

                if (contextPath != null && (!"".equals(contextPath))) {
                    if (!url.startsWith("/")) {
                        url = "/" + url;
                    }
                    if (!url.startsWith(contextPath)) {
                        url = contextPath + url;
                    }
                }
            }
            return url;

        }
        
}
