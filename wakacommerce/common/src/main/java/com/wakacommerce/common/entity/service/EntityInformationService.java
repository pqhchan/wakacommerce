
package com.wakacommerce.common.entity.service;

import com.wakacommerce.common.entity.dto.EntityInformationDto;
import com.wakacommerce.common.site.domain.Site;

/**
 *
 * @ hui
 */
public interface EntityInformationService {

    EntityInformationDto buildEntityInformationForObject(Object o);

    Long getBaseProfileIdForSite(Site site);

    boolean getOkayToUseSiteDiscriminator(Object o);

}
