
package com.wakacommerce.cms.page.service;

import com.wakacommerce.cms.field.domain.FieldDefinition;
import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.page.dto.PageDTO;


/**
 * Extension handler for {@link PageService}
 * 
 *Andre Azzolini (apazzolini)
 */
public interface PageServiceExtensionHandler extends ExtensionHandler {
    
    public static final String IS_KEY = "IS_KEY";
    
    /**
     * If this method returns something other than {@link ExtensionResultStatusType#NOT_HANDLED}, the result variable
     * in the {@link ExtensionResultHolder} will hold the associated {@link FieldDefinition} for the given {@link Page}
     * and field key.
     * 
     * @param erh
     * @param page
     * @param fieldKey
     * @return
     */
    ExtensionResultStatusType getFieldDefinition(ExtensionResultHolder<FieldDefinition> erh, Page page,
            String fieldKey);

    /**
     * This method provides the opportunity to modify the page fields associated with the pageDto 
     * {@link ExtensionResultHolder}.    Modifying classes should clone the DTO and adjust fields.
     * 
     * @param erh
     * @param pageDto
     * @param page
     * @return
     */
    ExtensionResultStatusType overridePageDto(ExtensionResultHolder<PageDTO> erh, PageDTO pageDto, Page page);

}
