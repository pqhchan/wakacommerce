package com.wakacommerce.core.web.resolver;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

import java.io.InputStream;

import javax.annotation.Resource;

@Service("blDatabaseResourceResolver")
public class DatabaseResourceResolver implements IResourceResolver {
    
    @Override
    public String getName() {
        return "BL_DATABASE";
    }
    
    @Resource(name = "blDatabaseResourceResolverExtensionManager")
    protected DatabaseResourceResolverExtensionManager extensionManager;

    @Override
    public InputStream getResourceAsStream(TemplateProcessingParameters params, String resourceName) {
        ExtensionResultHolder erh = new ExtensionResultHolder();
        ExtensionResultStatusType result = extensionManager.getProxy().resolveResource(erh, params, resourceName);
        if (result ==  ExtensionResultStatusType.HANDLED) {
            return (InputStream) erh.getContextMap().get(DatabaseResourceResolverExtensionHandler.IS_KEY);
        }
        return null;
    }

}
