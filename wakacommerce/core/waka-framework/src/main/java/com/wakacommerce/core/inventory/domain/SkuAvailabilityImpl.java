
package com.wakacommerce.core.inventory.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.inventory.service.type.AvailabilityStatusType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * The Class SkuAvailabilityImpl is the default implementation of {@link SkuAvailability}.
 * <br>
 * <br>
 * This class is retrieved using the AvailabilityService.   The service allows availability to be
 * be location specific (e.g. for store specific inventory availability)
 * <br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations.
 * The Entity references the following tables:
 * BLC_SKU_AVAILABILITY
 *
 * @see {@link Sku}
 * 
 * 
 * @deprecated This is no longer required and is instead implemented as a third-party inventory module
 */
@Deprecated
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_AVAILABILITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blInventoryElements")
public class SkuAvailabilityImpl implements SkuAvailability {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(generator = "SkuAvailabilityId")
    @GenericGenerator(
        name="SkuAvailabilityId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SkuAvailabilityImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.inventory.domain.SkuAvailabilityImpl")
        }
    )
    @Column(name = "SKU_AVAILABILITY_ID")
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Sku_Availability_ID", group = "SkuAvailabilityImpl_Primary_Key", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    /** The sale price. */
    @Column(name = "SKU_ID")
    @Index(name="SKUAVAIL_SKU_INDEX", columnNames={"SKU_ID"})
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Sku_ID", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long skuId;

    /** The retail price. */
    @Column(name = "LOCATION_ID")
    @Index(name="SKUAVAIL_LOCATION_INDEX", columnNames={"LOCATION_ID"})
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Location_ID", group = "SkuAvailabilityImpl_Description")
    protected Long locationId;

    /** The quantity on hand. */
    @Column(name = "QTY_ON_HAND")
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Quantity_On_Hand", group = "SkuAvailabilityImpl_Description")
    protected Integer quantityOnHand;

    /** The reserve quantity. */
    @Column(name = "RESERVE_QTY")
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Reserve_Quantity", group = "SkuAvailabilityImpl_Description")
    protected Integer reserveQuantity;

    /** The description. */
    @Column(name = "AVAILABILITY_STATUS")
    @Index(name="SKUAVAIL_STATUS_INDEX", columnNames={"AVAILABILITY_STATUS"})
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Availability_Status", group = "SkuAvailabilityImpl_Description", fieldType= SupportedFieldType.BROADLEAF_ENUMERATION, broadleafEnumeration="com.wakacommerce.core.inventory.service.type.AvailabilityStatusType")
    protected String availabilityStatus;

    /** The date this product will be available. */
    @Column(name = "AVAILABILITY_DATE")
    @AdminPresentation(friendlyName = "SkuAvailabilityImpl_Available_Date", group = "SkuAvailabilityImpl_Description")
    protected Date availabilityDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getLocationId() {
        return locationId;
    }

    @Override
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    @Override
    public Long getSkuId() {
        return skuId;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public void setQuantityOnHand(Integer qoh) {
        this.quantityOnHand = qoh;
    }

    @Override
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public Date getAvailabilityDate() {
        return availabilityDate;
    }

    @Override
    public void setAvailabilityDate(Date availabilityDate) {
        this.availabilityDate = availabilityDate;
    }
    
    @Override
    public AvailabilityStatusType getAvailabilityStatus() {
        return AvailabilityStatusType.getInstance(availabilityStatus);
    }

    @Override
    public void setAvailabilityStatus(final AvailabilityStatusType availabilityStatus) {
        if (availabilityStatus != null) {
            this.availabilityStatus = availabilityStatus.getType();
        }
    }

    /**
     * Returns the reserve quantity.   Nulls will be treated the same as 0.
     * Implementations may want to manage a reserve quantity at each location so that the
     * available quantity for purchases is the quantityOnHand - reserveQuantity.
     */
    @Override
    public Integer getReserveQuantity() {
        return reserveQuantity;
    }

    /**
     * Sets the reserve quantity.
     * Implementations may want to manage a reserve quantity at each location so that the
     * available quantity for purchases is the quantityOnHand - reserveQuantity.
     */
    @Override
    public void setReserveQuantity(Integer reserveQuantity) {
        this.reserveQuantity = reserveQuantity;
    }

    /**
     * Returns the getQuantityOnHand() - getReserveQuantity().
     * Preferred implementation is to return null if getQuantityOnHand() is null and to treat
     * a null in getReserveQuantity() as ZERO.
     */
    @Override
    public Integer getAvailableQuantity() {
        if (getQuantityOnHand() == null || getReserveQuantity() == null) {
            return getQuantityOnHand();
        } else {
            return getQuantityOnHand() - getReserveQuantity();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
        result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        SkuAvailabilityImpl other = (SkuAvailabilityImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (locationId == null) {
            if (other.locationId != null)
                return false;
        } else if (!locationId.equals(other.locationId))
            return false;
        if (skuId == null) {
            if (other.skuId != null)
                return false;
        } else if (!skuId.equals(other.skuId))
            return false;
        return true;
    }
}
