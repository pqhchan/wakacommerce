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

    public List<WakaThymeleafViewResolverExtensionHandler> getHandlers() {
        if (WakaRequestContext.getWakaRequestContext().getAdmin()) {
            return EMPTY_LIST;
        } else {
            return super.getHandlers();
        }
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
