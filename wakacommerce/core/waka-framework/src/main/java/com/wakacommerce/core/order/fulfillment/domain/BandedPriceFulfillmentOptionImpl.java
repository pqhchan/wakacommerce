
package com.wakacommerce.core.order.fulfillment.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.FulfillmentOptionImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 *  
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPT_BANDED_PRC")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "Banded Price Fulfillment Option")
public class BandedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements BandedPriceFulfillmentOption {

    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy="option", targetEntity=FulfillmentPriceBandImpl.class)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    protected List<FulfillmentPriceBand> bands = new ArrayList<FulfillmentPriceBand>();

    @Override
    public List<FulfillmentPriceBand> getBands() {
        return bands;
    }

    @Override
    public void setBands(List<FulfillmentPriceBand> bands) {
        this.bands = bands;
    }

    @Override
    public CreateResponse<BandedPriceFulfillmentOption> createOrRetrieveCopyInstance(MultiTenantCopyContext context)
            throws CloneNotSupportedException {
        CreateResponse<BandedPriceFulfillmentOption> createResponse = super.createOrRetrieveCopyInstance(context);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        BandedPriceFulfillmentOption myClone = createResponse.getClone();

        for (FulfillmentPriceBand band : bands) {
            myClone.getBands().add(band);
        }

        return createResponse;
    }

}
