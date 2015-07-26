
package com.wakacommerce.core.offer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.currency.util.CurrencyCodeIdentifiable;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationToOneLookup;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeEntry;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverride;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverrides;
import com.wakacommerce.common.presentation.override.PropertyType;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;
import com.wakacommerce.core.order.domain.OrderItemPriceDetailImpl;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ITEM_DTL_ADJ")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
@AdminPresentationMergeOverrides(
    {
        @AdminPresentationMergeOverride(name = "", mergeEntries =
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY,
                                            booleanOverrideValue = true))
    }
)
public class OrderItemPriceDetailAdjustmentImpl implements OrderItemPriceDetailAdjustment, CurrencyCodeIdentifiable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "OrderItemPriceDetailAdjustmentId")
    @GenericGenerator(
        name = "OrderItemPriceDetailAdjustmentId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name = "segment_value", value = "OrderItemPriceDetailAdjustmentImpl"),
            @Parameter(name = "entity_name", value = "com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustmentImpl")
        }
    )
    @Column(name = "ORDER_ITEM_DTL_ADJ_ID")
    protected Long id;

    @ManyToOne(targetEntity = OrderItemPriceDetailImpl.class)
    @JoinColumn(name = "ORDER_ITEM_PRICE_DTL_ID")
    @AdminPresentation(excluded = true)
    protected OrderItemPriceDetail orderItemPriceDetail;

    @ManyToOne(targetEntity = OfferImpl.class, optional=false)
    @JoinColumn(name = "OFFER_ID")
    @AdminPresentation(friendlyName = "OrderItemPriceDetailAdjustmentImpl_Offer", order=1000,
            prominent = true, gridOrder = 1000)
    @AdminPresentationToOneLookup()
    protected Offer offer;

    @Column(name = "OFFER_NAME")
    protected String offerName;

    @Column(name = "ADJUSTMENT_REASON", nullable=false)
    @AdminPresentation(friendlyName = "OrderItemPriceDetailAdjustmentImpl_reason", order = 1,
            group = "OrderItemPriceDetailAdjustmentImpl_Description")
    protected String reason;

    @Column(name = "ADJUSTMENT_VALUE", nullable=false, precision=19, scale=5)
    @AdminPresentation(friendlyName = "OrderItemPriceDetailAdjustmentImpl_value", order = 2,
            group = "OrderItemPriceDetailAdjustmentImpl_Description", fieldType = SupportedFieldType.MONEY, prominent = true)
    protected BigDecimal value = Money.ZERO.getAmount();

    @Column(name = "APPLIED_TO_SALE_PRICE")
    @AdminPresentation(friendlyName = "OrderItemPriceDetailAdjustmentImpl_appliedToSalePrice", order = 3,
            group = "OrderItemPriceDetailAdjustmentImpl_Description")
    protected boolean appliedToSalePrice;
    
    @Transient
    protected Money retailValue;

    @Transient
    protected Money salesValue;


    @Override
    public void init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason) {
        this.orderItemPriceDetail = orderItemPriceDetail;
        setOffer(offer);
        if (reason == null) {
            this.reason = reason;
            this.reason = offer.getName();
        }

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public OrderItemPriceDetail getOrderItemPriceDetail() {
        return orderItemPriceDetail;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public String getOfferName() {
        return offerName;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public void setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail) {
        this.orderItemPriceDetail = orderItemPriceDetail;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
        if (offer != null) {
            this.offerName = offer.getMarketingMessage() != null ? offer.getMarketingMessage() : offer.getName();
        }
    }

    @Override
    public void setOfferName(String offerName) {
        this.offerName = offer.getName();
    }

    protected BroadleafCurrency getCurrency() {
        return getOrderItemPriceDetail().getOrderItem().getOrder().getCurrency();
    }

    @Override
    public Money getValue() {
        return value == null ? null : BroadleafCurrencyUtils.getMoney(value, getCurrency());
    }
    
    @Override
    public void setValue(Money value) {
        this.value = value.getAmount();
    }

    @Override
    public boolean isAppliedToSalePrice() {
        return appliedToSalePrice;
    }

    @Override
    public void setAppliedToSalePrice(boolean appliedToSalePrice) {
        this.appliedToSalePrice = appliedToSalePrice;
    }

    @Override
    public Money getRetailPriceValue() {
        if (retailValue == null) {
            return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());
        }
        return this.retailValue;
    }

    @Override
    public void setRetailPriceValue(Money retailPriceValue) {
        this.retailValue = retailPriceValue;
    }

    @Override
    public Money getSalesPriceValue() {
        if (salesValue == null) {
            return BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());
        }
        return salesValue;
    }

    @Override
    public void setSalesPriceValue(Money salesPriceValue) {
        this.salesValue = salesPriceValue;
    }

    @Override
    public String getCurrencyCode() {
        if (getCurrency() != null) {
            return getCurrency().getCurrencyCode();
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((offer == null) ? 0 : offer.hashCode());
        result = prime * result + ((orderItemPriceDetail == null) ? 0 : orderItemPriceDetail.hashCode());
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        OrderItemPriceDetailAdjustmentImpl other = (OrderItemPriceDetailAdjustmentImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (offer == null) {
            if (other.offer != null) {
                return false;
            }
        } else if (!offer.equals(other.offer)) {
            return false;
        }
        if (orderItemPriceDetail == null) {
            if (other.orderItemPriceDetail != null) {
                return false;
            }
        } else if (!orderItemPriceDetail.equals(other.orderItemPriceDetail)) {
            return false;
        }
        if (reason == null) {
            if (other.reason != null) {
                return false;
            }
        } else if (!reason.equals(other.reason)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public <G extends OrderItemPriceDetailAdjustment> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OrderItemPriceDetailAdjustment cloned = createResponse.getClone();
        cloned.setOfferName(offerName);
        cloned.setAppliedToSalePrice(appliedToSalePrice);
        // dont clone
        cloned.setOrderItemPriceDetail(orderItemPriceDetail);
        cloned.setSalesPriceValue(getSalesPriceValue());
        cloned.setRetailPriceValue(getRetailPriceValue());
        cloned.setReason(reason);
        cloned.setValue(getValue());
        return createResponse;
    }
}
