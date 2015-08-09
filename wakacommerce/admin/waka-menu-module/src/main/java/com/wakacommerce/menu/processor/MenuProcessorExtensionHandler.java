
package com.wakacommerce.menu.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface MenuProcessorExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType addAdditionalFieldsToModel(Arguments arguments, Element element);

}
