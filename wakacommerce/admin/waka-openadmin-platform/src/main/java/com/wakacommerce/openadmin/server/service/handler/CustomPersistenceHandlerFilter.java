
package com.wakacommerce.openadmin.server.service.handler;

/**
 *
 * @ hui
 */
public interface CustomPersistenceHandlerFilter {
    
    public boolean shouldUseHandler(String handlerClassName);

}
