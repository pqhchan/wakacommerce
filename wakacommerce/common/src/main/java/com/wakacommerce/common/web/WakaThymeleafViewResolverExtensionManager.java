package com.wakacommerce.common.web;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

import java.util.Collections;
import java.util.List;

@Service("blBroadleafThymeleafViewResolverExtensionManager")
public class WakaThymeleafViewResolverExtensionManager extends ExtensionManager<WakaThymeleafViewResolverExtensionHandler> {

    private List<WakaThymeleafViewResolverExtensionHandler> EMPTY_LIST = Collections.emptyList();

    public WakaThymeleafViewResolverExtensionManager() {
        super(WakaThymeleafViewResolverExtensionHandler.class);
    }

    @Override
    /**
     * Don't use this extension manager in the admin.
     */
    public List<WakaThymeleafViewResolverExtensionHandler> getHandlers() {
        if (WakaRequestContext.getWakaRequestContext().getAdmin()) {
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
