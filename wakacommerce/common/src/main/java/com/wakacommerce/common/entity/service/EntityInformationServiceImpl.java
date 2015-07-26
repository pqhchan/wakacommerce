
package com.wakacommerce.common.entity.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.entity.dto.EntityInformationDto;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.site.domain.Site;

import javax.annotation.Resource;

/**
 * 
 *bpolster
 *
 */
@Service("blEntityInformationService")
public class EntityInformationServiceImpl implements EntityInformationService {

    @Resource(name = "blEntityInformationServiceExtensionManager")
    protected EntityInformationServiceExtensionManager extensionManager;

    public EntityInformationDto buildEntityInformationForObject(Object o) {
        EntityInformationDto dto = createEntityInformationDto(o);
        extensionManager.getProxy().updateEntityInformationDto(dto, o);
        return dto;
    }

    @Override
    public Long getBaseProfileIdForSite(Site site) {
        ExtensionResultHolder<Long> erh = new ExtensionResultHolder<Long>();
        extensionManager.getProxy().getBaseProfileIdForSite(site, erh);
        return erh.getResult();
    }

    @Override
    public boolean getOkayToUseSiteDiscriminator(Object o) {
        ExtensionResultHolder<Boolean> erh = new ExtensionResultHolder<Boolean>();
        erh.setResult(Boolean.FALSE);
        extensionManager.getProxy().getOkayToUseSiteDiscriminator(o, erh);
        return erh.getResult();
    }

    /**
     * Factory method for instantiating the {@link EntityInformationDto}
     * @return
     */
    protected EntityInformationDto createEntityInformationDto(Object o) {
        return new EntityInformationDto();
    }

}
