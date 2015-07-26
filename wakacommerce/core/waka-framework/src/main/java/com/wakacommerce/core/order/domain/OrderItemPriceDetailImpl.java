
package com.wakacommerce.core.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.currency.util.CurrencyCodeIdentifiable;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeEntry;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverride;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverrides;
import com.wakacommerce.common.presentation.override.PropertyType;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustmentImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ITEM_PRICE_DTL")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
@AdminPresentationMergeOverrides(
    {
        @AdminPresentationMergeOverride(name = "", mergeEntries =
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY,
                                            booleanOverrideValue = true))
    }
)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class OrderItemPriceDetailImpl implements OrderItemPriceDetail, CurrencyCodeIdentifiable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "OrderItemPriceDetailId")
    @GenericGenerator(
        name="OrderItemPriceDetailId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OrderItemPriceDetailImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.order.domain.OrderItemPriceDetailImpl")
        }
    )
    @Column(name = "ORDER_ITEM_PRICE_DTL_ID")
    @AdminPresentation(friendlyName = "OrderItemPriceDetailImpl_Id", group = "OrderItemPriceDetailImpl_Primary_Key", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @ManyToOne(targetEntity = OrderItemImpl.class)
    @JoinColumn(name = "ORDER_ITEM_ID")
    @AdminPresentation(excluded = true)
    protected OrderItem orderItem;

    @OneToMany(mappedBy = "orderItemPriceDetail", targetEntity = OrderItemPriceDetailAdjustmentImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blOrderElements")
    @AdminPresentationCollection(addType = AddMethodType.PERSIST, friendlyName = "OrderItemPriceDetailImpl_orderItemPriceDetailAdjustments")
    protected List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments = new ArrayList<OrderItemPriceDetailAdjustment>();

    @Column(name = "QUANTITY", nullable=false)
    @AdminPresentation(friendlyName = "OrderItemPriceDetailImpl_quantity", order = 5, group = "OrderItemPriceDetailImpl_Pricing", prominent = true)
    protected int quantity;

    @Column(name = "USE_SALE_PRICE")
    @AdminPresentation(friendlyName = "OrderItemPriceDetailImpl_useSalePrice", order = 5, group = "OrderItemPriceDetailImpl_Pricing", prominent = true)
    protected Boolean useSalePrice = true;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public OrderItem getOrderItem() {
        return orderItem;
    }

    @Override
    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public List<OrderItemPriceDetailAdjustment> getOrderItemPriceDetailAdjustments() {
        return orderItemPriceDetailAdjustments;
    }

    @Override
    public void setOrderItemAdjustments(List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments) {
        this.orderItemPriceDetailAdjustments = orderItemPriceDetailAdjustments;
        
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    protected BroadleafCurrency getCurrency() {
        return getOrderItem().getOrder().getCurrency();
    }

    @Override
    public Money getAdjustmentValue() {
        Money adjustmentValue = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency());
        for (OrderItemPriceDetailAdjustment adjustment : orderItemPriceDetailAdjustments) {
            adjustmentValue = adjustmentValue.add(adjustment.getValue());
        }
        return adjustmentValue;
    }

    @Override
    public Money getTotalAdjustmentValue() {
        return getAdjustmentValue().multiply(quantity);
    }

    @Override
    public Money getTotalAdjustedPrice() {
        Money basePrice = orderItem.getPriceBeforeAdjustments(getUseSalePrice());
        return basePrice.multiply(quantity).subtract(getTotalAdjustmentValue());
    }

    @Override
    public boolean getUseSalePrice() {
        if (useSalePrice == null) {
            return false;
        } else {
            return useSalePrice;
        }
    }

    @Override
    public void setUseSalePrice(boolean useSalePrice) {
        this.useSalePrice = Boolean.valueOf(useSalePrice);
    }

    @Override
    public String getCurrencyCode() {
        if (getCurrency() != null) {
            return getCurrency().getCurrencyCode();
        }
        return null;
    }

    @Override
    public <G extends OrderItemPriceDetail> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OrderItemPriceDetail cloned = createResponse.getClone();
        cloned.setQuantity(quantity);
        cloned.setUseSalePrice(useSalePrice);
        // dont clone
        cloned.setOrderItem(orderItem);
        for(OrderItemPriceDetailAdjustment entry : orderItemPriceDetailAdjustments){
            OrderItemPriceDetailAdjustment clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            clonedEntry.setOrderItemPriceDetail(cloned);
            cloned.getOrderItemPriceDetailAdjustments().add(clonedEntry);
        }
        return  createResponse;
    }
}
