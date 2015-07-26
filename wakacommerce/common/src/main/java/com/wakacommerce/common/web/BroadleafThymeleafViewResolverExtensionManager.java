
package com.wakacommerce.common.web;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

import java.util.Collections;
import java.util.List;


/**
 *Andre Azzolini (apazzolini)
 */
@Service("blBroadleafThymeleafViewResolverExtensionManager")
public class BroadleafThymeleafViewResolverExtensionManager extends ExtensionManager<BroadleafThymeleafViewResolverExtensionHandler> {

    private List<BroadleafThymeleafViewResolverExtensionHandler> EMPTY_LIST = Collections.emptyList();

    public BroadleafThymeleafViewResolverExtensionManager() {
        super(BroadleafThymeleafViewResolverExtensionHandler.class);
    }

    @Override
    /**
     * Don't use this extension manager in the admin.
     */
    public List<BroadleafThymeleafViewResolverExtensionHandler> getHandlers() {
        if (BroadleafRequestContext.getBroadleafRequestContext().getAdmin()) {
            return EMPTY_LIST;
        } else {
            return super.getHandlers();
        }
    }


    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
