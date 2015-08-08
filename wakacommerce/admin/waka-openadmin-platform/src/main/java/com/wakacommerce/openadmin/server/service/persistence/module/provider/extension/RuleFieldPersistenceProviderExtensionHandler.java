  
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 * For internal usage. Allows extending API calls without subclassing the entity.
 *
 * 
 */
public interface RuleFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType transformId(QuantityBasedRule rule, ExtensionResultHolder<Long> resultHolder);

    ExtensionResultStatusType postAdd(Object rule, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType postUpdate(Object rule);

    ExtensionResultStatusType establishDirtyState(Object rule, ExtensionResultHolder<Boolean> resultHolder);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;

}
