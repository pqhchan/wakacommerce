package com.wakacommerce.cms.structure.service;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;

import java.util.List;

public interface StructuredContentServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType populateAdditionalStructuredContentFields(StructuredContent sc, StructuredContentDTO dto, boolean secure);

    public ExtensionResultStatusType modifyStructuredContentDtoList(List<StructuredContentDTO> structuredContentList,
            ExtensionResultHolder resultHolder);
}
