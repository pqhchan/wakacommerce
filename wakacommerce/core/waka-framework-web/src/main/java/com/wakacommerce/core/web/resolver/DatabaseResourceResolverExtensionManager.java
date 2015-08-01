
package com.wakacommerce.core.web.resolver;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * ,  
 */
@Service("blDatabaseResourceResolverExtensionManager")
public class DatabaseResourceResolverExtensionManager extends ExtensionManager<DatabaseResourceResolverExtensionHandler> {

    public DatabaseResourceResolverExtensionManager() {
        super(DatabaseResourceResolverExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return false;
    }
}
