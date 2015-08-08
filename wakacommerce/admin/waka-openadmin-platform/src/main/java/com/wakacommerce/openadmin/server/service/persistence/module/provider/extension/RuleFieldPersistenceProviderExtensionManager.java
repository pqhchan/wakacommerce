  
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * For internal usage. Allows extension of entity API calls without subclassing the entity.
 *
 * 
 */
@Service("blRuleFieldPersistenceProviderExtensionManager")
public class RuleFieldPersistenceProviderExtensionManager extends ExtensionManager<RuleFieldPersistenceProviderExtensionHandler> {

    public RuleFieldPersistenceProviderExtensionManager() {
        super(RuleFieldPersistenceProviderExtensionHandler.class);
    }

}
