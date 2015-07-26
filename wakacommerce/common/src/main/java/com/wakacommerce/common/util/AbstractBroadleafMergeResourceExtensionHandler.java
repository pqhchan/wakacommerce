
package com.wakacommerce.common.util;

import java.util.Locale;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public class AbstractBroadleafMergeResourceExtensionHandler extends AbstractExtensionHandler implements BroadleafMergeResourceExtensionHandler {

    @Override
    public ExtensionResultStatusType resolveMessageSource(String code, Locale locale, ExtensionResultHolder<String> result) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
