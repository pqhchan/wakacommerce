
package com.wakacommerce.common.entity.service;

import com.wakacommerce.common.entity.dto.EntityInformationDto;
import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;

public class AbstractEntityInformationServiceExtensionHandler extends AbstractExtensionHandler
        implements EntityInformationServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType updateEntityInformationDto(EntityInformationDto dto, Object entityInstance) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getBaseProfileIdForSite(Site site, ExtensionResultHolder<Long> erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getOkayToUseSiteDiscriminator(Object o, ExtensionResultHolder<Boolean> erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
