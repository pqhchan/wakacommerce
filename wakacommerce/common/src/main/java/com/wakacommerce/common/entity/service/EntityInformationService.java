
package com.wakacommerce.common.entity.service;

import com.wakacommerce.common.entity.dto.EntityInformationDto;
import com.wakacommerce.common.site.domain.Site;

/**
 * Class that provides guidance to modules that need to react when running in a multi-tenant mode.   Especially, 
 * when an entity is associated with a Profile or Catalog.
 * 
 *bpolster
 *
 */
public interface EntityInformationService {

    /**
     * Given an object, populates {@link EntityInformationDto} with the associated site, profile, and catalog ids.
     * 
     * This method does nothing by default and instead relies on the {@link EntityInformationServiceExtensionManager} to
     * populate the correct values.     Designed for use with the commercial "workflow" and "multi-tenant" modules.
     * 
     * @param o
     * @return
     */
    EntityInformationDto buildEntityInformationForObject(Object o);

    /**
     * Given a Site object, returns the Base Profile Id if one exists.    This method provides a hook
     * for Broadleaf MultiTenant functionality
     * 
     * @param o
     * @return
     */
    Long getBaseProfileIdForSite(Site site);

    /**
     * Given an entity instance, returns true if the object has access to a Site Discriminator.
     * 
     * @param o
     * @return
     */
    boolean getOkayToUseSiteDiscriminator(Object o);

}
