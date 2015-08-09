
package com.wakacommerce.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.web.WakaRequestContext;

/**
 *
 * @ hui
 */
@Service("blBLCMessageUtils")
public class BLCMessageUtils implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;

    public static String getMessage(String code) {
        return getMessage(code, (Object) null);
    }

    public static String getMessage(String code, Object... args) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        return getMessageSource().getMessage(code, args, brc.getJavaLocale());
    }

    protected static MessageSource getMessageSource() {
        return (MessageSource) applicationContext.getBean("messageSource");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BLCMessageUtils.applicationContext = applicationContext;
    }

}
