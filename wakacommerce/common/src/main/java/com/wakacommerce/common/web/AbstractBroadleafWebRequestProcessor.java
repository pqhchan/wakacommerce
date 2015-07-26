
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;


/**
 * Provides no-op implementations to optional methods
 */
public abstract class AbstractBroadleafWebRequestProcessor implements BroadleafWebRequestProcessor {

    
    public void postProcess(WebRequest request) {
        // nada
    }

}
