
package com.wakacommerce.common.entity.dto;

import com.wakacommerce.common.entity.service.EntityInformationService;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;

/**
 * This class holds information about an entity.
 * 
 * It is populated by a call to {@link EntityInformationService}.    The out-of-box implementation 
 * is a placeholder service.
 * 
 * The enterprise-workflow and multi-tenant modules add functionality to properly populate the dto.
 *    
 * 
 *
 */
public class EntityInformationDto {

    private Long profileId;
    private Long catalogId;
    private Long owningSiteId;

    /**
     * For entities that implement {@link ProfileEntity}, returns the value of the profile with which 
     * the entity is associated.    Otherwise, returns null.
     * 
     * @return the profileId
     * 
     */
    public Long getProfileId() {
        return profileId;
    }

    /**
     * Sets the profileId.  Typically called by {@link EntityInformationService} when creating this dto.
     *
     * @param profileId the profileId to set
     * @see EntityInformationDto#getProfileId()
     */
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    /**
     * For entities that implement {@link DirectCopyTransformTypes.MULTITENANT_CATALOG}, returns the id of the catalog 
     * with which the entity is associated.    Otherwise, returns null.
     * 
     * @return the catalogId
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * Sets the catalogId.  Typically called by {@link EntityInformationService} when creating this dto.
     *
     * @param catalogId the catalogId to set
     * @see EntityInformationDto#getCatalogId()()
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * Return the site that owns this record.     If not in a MultiTenant scenario or if the entity is not
     * setup for MultiTenant, this method will return null.
     * @return the siteId
     */
    public Long getOwningSiteId() {
        return owningSiteId;
    }

    /**
     * Sets the siteId that owns this record.
     * @param siteId the siteId to set
     */
    public void setOwningSiteId(Long owningSiteId) {
        this.owningSiteId = owningSiteId;
    }

    /**
     * Returns true if this dto represents a profile entity
     * @return
     */
    public boolean isProfileEntity() {
        return getProfileId() != null;
    }

    /**
     * Returns true if this dto represents a catalog entity
     * @return
     */
    public boolean isCatalogEntity() {
        return getCatalogId() != null;
    }
}
