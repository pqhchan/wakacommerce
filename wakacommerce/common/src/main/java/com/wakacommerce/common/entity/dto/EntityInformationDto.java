
package com.wakacommerce.common.entity.dto;

import com.wakacommerce.common.entity.service.EntityInformationService;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;

/**
 *
 * @ hui
 */
public class EntityInformationDto {

    private Long profileId;
    private Long catalogId;
    private Long owningSiteId;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getOwningSiteId() {
        return owningSiteId;
    }

    public void setOwningSiteId(Long owningSiteId) {
        this.owningSiteId = owningSiteId;
    }

    public boolean isProfileEntity() {
        return getProfileId() != null;
    }

    public boolean isCatalogEntity() {
        return getCatalogId() != null;
    }
}
