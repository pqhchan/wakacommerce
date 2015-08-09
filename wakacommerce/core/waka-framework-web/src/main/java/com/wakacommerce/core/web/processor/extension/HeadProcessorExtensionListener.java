
package com.wakacommerce.core.web.processor.extension;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 *
 * @ hui
 */
public interface HeadProcessorExtensionListener {

    public void processAttributeValues(Arguments arguments, Element element);

}
