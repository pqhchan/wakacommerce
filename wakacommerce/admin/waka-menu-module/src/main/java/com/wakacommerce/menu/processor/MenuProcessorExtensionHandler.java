
package com.wakacommerce.menu.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *Elbert Bautista (elbertbautista)
 */
public interface MenuProcessorExtensionHandler extends ExtensionHandler {

    /**
     * This method will add any additional attributes to the model that the extension needs
     *
     * @param arguments - the Thymeleaf Processor arguments
     * @param element - the Thymeleaf Processor element
     * @return - ExtensionResultStatusType
     */
    public ExtensionResultStatusType addAdditionalFieldsToModel(Arguments arguments, Element element);

}
