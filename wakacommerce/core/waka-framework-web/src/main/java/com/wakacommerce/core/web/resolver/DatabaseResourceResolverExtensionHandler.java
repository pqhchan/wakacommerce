
package com.wakacommerce.core.web.resolver;

import org.thymeleaf.TemplateProcessingParameters;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

import java.io.InputStream;


/**
 *
 * @ hui
 */
public interface DatabaseResourceResolverExtensionHandler extends ExtensionHandler {
    
    public static final String IS_KEY = "IS_KEY";

    public ExtensionResultStatusType resolveResource(ExtensionResultHolder erh, 
            TemplateProcessingParameters params, String resourceName);

}
