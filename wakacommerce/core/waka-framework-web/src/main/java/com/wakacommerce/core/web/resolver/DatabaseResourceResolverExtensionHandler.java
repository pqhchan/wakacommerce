
package com.wakacommerce.core.web.resolver;

import org.thymeleaf.TemplateProcessingParameters;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

import java.io.InputStream;


/**
 * Extension handler for resolving templates from the database.
 * 
 *Andre Azzolini (apazzolini), bpolster
 */
public interface DatabaseResourceResolverExtensionHandler extends ExtensionHandler {
    
    public static final String IS_KEY = "IS_KEY";
    
    /**
     * If this method returns any of the handled conditions in {@link ExtensionResultStatusType},
     * the value keyed by {@link DatabaseResourceResolverExtensionHandler.IS_KEY} in the 
     * {@link ExtensionResultHolder}'s context map will be an {@link InputStream} of the resolved resource's
     * contents.
     * 
     * @param erh
     * @param params
     * @param resourceName
     * @return whether or not a resource was resolved
     */
    public ExtensionResultStatusType resolveResource(ExtensionResultHolder erh, 
            TemplateProcessingParameters params, String resourceName);

}
