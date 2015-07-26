
package com.wakacommerce.openadmin.server.service.handler;

/**
 *Jeff Fischer
 */
public interface CustomPersistenceHandlerFilter {
    
    public boolean shouldUseHandler(String handlerClassName);

}
