
package com.wakacommerce.openadmin.server.service.handler;

/**
 * 
 */
public interface CustomPersistenceHandlerFilter {
    
    public boolean shouldUseHandler(String handlerClassName);

}
