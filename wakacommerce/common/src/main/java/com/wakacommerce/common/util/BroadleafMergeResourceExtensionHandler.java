
package com.wakacommerce.common.util;

import java.util.Locale;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface BroadleafMergeResourceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType resolveMessageSource(String code, Locale locale, ExtensionResultHolder<String> result);

}
