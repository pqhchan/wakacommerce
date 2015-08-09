
package com.wakacommerce.core.order.fulfillment.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.currency.domain.BroadleafCurrencyImpl;
import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.core.order.domain.FulfillmentOptionImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * @ hui
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPTION_FIXED")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "Fixed Price Fulfillment")
public class FixedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements FixedPriceFulfillmentOption {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRICE", precision=19, scale=5, nullable=false)
    protected BigDecimal price;
    
    @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
    @JoinColumn(name = "CURRENCY_CODE")
    @AdminPresentation(excluded = true)
    protected BroadleafCurrency currency;

    @Override
    public Money getPrice() {
        return price == null ? null : BroadleafCurrencyUtils.getMoney(price, getCurrency());
    }

    @Override
    public void setPrice(Money price) {
        this.price = Money.toAmount(price);
    }

    @Override
    public BroadleafCurrency getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(BroadleafCurrency currency) {
        this.currency = currency;
    }

    @Override
    public CreateResponse<FixedPriceFulfillmentOption> createOrRetrieveCopyInstance(MultiTenantCopyContext context)
            throws CloneNotSupportedException {
        CreateResponse<FixedPriceFulfillmentOption> createResponse = super.createOrRetrieveCopyInstance(context);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        FixedPriceFulfillmentOption myClone = createResponse.getClone();
        myClone.setPrice(getPrice());
        myClone.setCurrency(currency);

        return createResponse;
    }
}
