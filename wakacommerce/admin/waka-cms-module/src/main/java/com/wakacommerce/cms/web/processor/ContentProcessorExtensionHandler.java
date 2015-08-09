package com.wakacommerce.cms.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.web.deeplink.DeepLink;

import java.util.List;

public interface ContentProcessorExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType addAdditionalFieldsToModel(Arguments arguments, Element element);

    public ExtensionResultStatusType addExtensionFieldDeepLink(List<DeepLink> links, Arguments arguments, Element element);

    public ExtensionResultStatusType postProcessDeepLinks(List<DeepLink> links);

}
