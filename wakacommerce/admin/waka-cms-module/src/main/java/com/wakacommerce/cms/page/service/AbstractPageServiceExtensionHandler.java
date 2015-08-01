
package com.wakacommerce.cms.page.service;

import com.wakacommerce.cms.field.domain.FieldDefinition;
import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.page.dto.PageDTO;


/**
 * 
 */
public abstract class AbstractPageServiceExtensionHandler extends AbstractExtensionHandler 
        implements PageServiceExtensionHandler {
    
    @Override
    public ExtensionResultStatusType getFieldDefinition(ExtensionResultHolder<FieldDefinition> erh, Page page, 
            String fieldKey) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType overridePageDto(ExtensionResultHolder<PageDTO> erh, PageDTO pageDto, Page page) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
