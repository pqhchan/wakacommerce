
package com.wakacommerce.common.entity.service;

import com.wakacommerce.common.entity.dto.EntityInformationDto;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;

/**
 *
 * @ hui
 */
public interface EntityInformationServiceExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType updateEntityInformationDto(EntityInformationDto dto, Object entityInstance);

    ExtensionResultStatusType getBaseProfileIdForSite(Site site, ExtensionResultHolder<Long> erh);

    ExtensionResultStatusType getOkayToUseSiteDiscriminator(Object o, ExtensionResultHolder<Boolean> erh);

}
