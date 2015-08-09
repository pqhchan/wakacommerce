package com.wakacommerce.common.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.i18n.service.TranslationConsiderationContext;
import com.wakacommerce.common.i18n.service.TranslationService;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.AbstractWakaWebRequestProcessor;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Component("blTranslationRequestProcessor")
public class TranslationRequestProcessor extends AbstractWakaWebRequestProcessor {
    
    @Resource(name = "blTranslationService")
    protected TranslationService translationService;
    
    protected boolean getTranslationEnabled() {
        return BLCSystemProperty.resolveBooleanSystemProperty("i18n.translation.enabled");
    }

    @Override
    public void process(WebRequest request) {
        TranslationConsiderationContext.setTranslationConsiderationContext(getTranslationEnabled());
        TranslationConsiderationContext.setTranslationService(translationService);
    }
}
