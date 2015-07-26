
package com.wakacommerce.openadmin.server.util;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *Jeff Fischer
 */
public class MetadataOverrideNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("override", new MetadataOverrideBeanDefinitionParser());
    }

}
