package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

public abstract class AbstractWakaWebRequestProcessor implements WakaWebRequestProcessor {

    
    public void postProcess(WebRequest request) {
        // nada
    }

}
