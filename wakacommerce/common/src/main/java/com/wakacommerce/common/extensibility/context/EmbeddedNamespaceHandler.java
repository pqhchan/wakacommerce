package com.wakacommerce.common.extensibility.context;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *
 * @ hui
 */
public class EmbeddedNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
	    registerBeanDefinitionParser("mergeImport", new EmbeddedBeanDefinitionParser());
	}

}
