
package com.wakacommerce.core.web.resolver;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blDatabaseResourceResolverExtensionManager")
public class DatabaseResourceResolverExtensionManager extends ExtensionManager<DatabaseResourceResolverExtensionHandler> {

    public DatabaseResourceResolverExtensionManager() {
        super(DatabaseResourceResolverExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return false;
    }
}
