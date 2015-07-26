
package com.wakacommerce.menu.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *Elbert Bautista (elbertbautista)
 */
public abstract class AbstractMenuProcessorExtensionHandler extends AbstractExtensionHandler
        implements MenuProcessorExtensionHandler {

    @Override
    public ExtensionResultStatusType addAdditionalFieldsToModel(Arguments arguments, Element element) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
