
package com.wakacommerce.cms.page.service;

import com.wakacommerce.cms.field.domain.FieldDefinition;
import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.page.dto.PageDTO;


/**
 *
 * @ hui
 */
public interface PageServiceExtensionHandler extends ExtensionHandler {
    
    public static final String IS_KEY = "IS_KEY";

    ExtensionResultStatusType getFieldDefinition(ExtensionResultHolder<FieldDefinition> erh, Page page,
            String fieldKey);

    ExtensionResultStatusType overridePageDto(ExtensionResultHolder<PageDTO> erh, PageDTO pageDto, Page page);

}
