package com.wakacommerce.cms.structure.service;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;

import java.util.List;

public class AbstractStructuredContentServiceExtensionHandler extends AbstractExtensionHandler
        implements StructuredContentServiceExtensionHandler {

    public ExtensionResultStatusType populateAdditionalStructuredContentFields(StructuredContent sc,
            StructuredContentDTO dto, boolean secure) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }


    public ExtensionResultStatusType modifyStructuredContentDtoList(List<StructuredContentDTO> structuredContentList,
            ExtensionResultHolder resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
