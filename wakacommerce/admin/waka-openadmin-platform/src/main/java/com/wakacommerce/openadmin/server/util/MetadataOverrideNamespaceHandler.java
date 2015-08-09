
package com.wakacommerce.openadmin.server.util;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *
 * @ hui
 */
public class MetadataOverrideNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("override", new MetadataOverrideBeanDefinitionParser());
    }

}
