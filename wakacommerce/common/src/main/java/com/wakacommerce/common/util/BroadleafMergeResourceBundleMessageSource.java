package com.wakacommerce.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;

import com.wakacommerce.common.extension.ExtensionResultHolder;

import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Resource;


/**
 *
 * @ hui
 */
public class BroadleafMergeResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    @Resource(name = "blBroadleafMergeResourceExtensionManager")
    protected BroadleafMergeResourceExtensionManager extensionManager;

    @Override
    public void setBasenames(String... basenames) {
        CollectionUtils.reverseArray(basenames);
        super.setBasenames(basenames);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        ExtensionResultHolder<String> messageHolder = new ExtensionResultHolder<String>();
        extensionManager.getProxy().resolveMessageSource(code, locale, messageHolder);
        if (StringUtils.isNotBlank(messageHolder.getResult())) {
            return createMessageFormat(messageHolder.getResult(), locale);
        }

        return super.resolveCode(code, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        ExtensionResultHolder<String> messageHolder = new ExtensionResultHolder<String>();
        extensionManager.getProxy().resolveMessageSource(code, locale, messageHolder);
        if (StringUtils.isNotBlank(messageHolder.getResult())) {
            return messageHolder.getResult();
        }

        return super.resolveCodeWithoutArguments(code, locale);
    }

}
