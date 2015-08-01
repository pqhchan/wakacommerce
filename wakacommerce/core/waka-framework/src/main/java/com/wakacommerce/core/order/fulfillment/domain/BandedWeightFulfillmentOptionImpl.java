
package com.wakacommerce.core.order.fulfillment.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.FulfillmentOptionImpl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 
 *  
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPT_BANDED_WGT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "Banded Weight Fulfillment Option")
public class BandedWeightFulfillmentOptionImpl extends FulfillmentOptionImpl implements BandedWeightFulfillmentOption {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy="option", targetEntity=FulfillmentWeightBandImpl.class)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    protected List<FulfillmentWeightBand> bands;

    @Override
    public List<FulfillmentWeightBand> getBands() {
        return bands;
    }

    @Override
    public void setBands(List<FulfillmentWeightBand> bands) {
        this.bands = bands;
    }

    @Override
    public CreateResponse<BandedWeightFulfillmentOption> createOrRetrieveCopyInstance(MultiTenantCopyContext context)
            throws CloneNotSupportedException {
        CreateResponse<BandedWeightFulfillmentOption> createResponse = super.createOrRetrieveCopyInstance(context);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        BandedWeightFulfillmentOption myClone = createResponse.getClone();

        for (FulfillmentWeightBand band : bands) {
            myClone.getBands().add(band);
        }

        return createResponse;
    }
}
