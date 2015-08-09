
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.offer.domain.CandidateItemOffer;
import com.wakacommerce.core.offer.domain.OrderItemAdjustment;
import com.wakacommerce.core.order.service.type.OrderItemType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface OrderItem extends Serializable, Cloneable {

    Long getId();

    void setId(Long id);

    Order getOrder();

    void setOrder(Order order);

    Money getRetailPrice();

    void setRetailPrice(Money retailPrice);

    void setRetailPriceOverride(boolean override);

    boolean isRetailPriceOverride();

    Money getSalePrice();

    void setSalePrice(Money salePrice);

    void setSalePriceOverride(boolean override);

    boolean isSalePriceOverride();

    @Deprecated
    Money getAdjustmentValue();

    @Deprecated
    Money getPrice();

    void setPrice(Money price);

    int getQuantity();

    void setQuantity(int quantity);

    List<OrderItemPriceDetail> getOrderItemPriceDetails();

    void setOrderItemPriceDetails(List<OrderItemPriceDetail> orderItemPriceDetails);

    Category getCategory();

    void setCategory(Category category);

    List<CandidateItemOffer> getCandidateItemOffers();

    void setCandidateItemOffers(List<CandidateItemOffer> candidateItemOffers);

    @Deprecated
    List<OrderItemAdjustment> getOrderItemAdjustments();

    @Deprecated
    void setOrderItemAdjustments(List<OrderItemAdjustment> orderItemAdjustments);

    List<OrderItemQualifier> getOrderItemQualifiers();

    void setOrderItemQualifiers(List<OrderItemQualifier> orderItemQualifiers);

    PersonalMessage getPersonalMessage();

    void setPersonalMessage(PersonalMessage personalMessage);

    boolean isInCategory(String categoryName);

    GiftWrapOrderItem getGiftWrapOrderItem();

    void setGiftWrapOrderItem(GiftWrapOrderItem giftWrapOrderItem);

    OrderItemType getOrderItemType();

    void setOrderItemType(OrderItemType orderItemType);

    @Deprecated
    Money getTaxablePrice();

    boolean getIsOnSale();

    boolean isDiscountingAllowed();

    void setDiscountingAllowed(boolean discountingAllowed);

    boolean getIsDiscounted();

    String getName();

    boolean updateSaleAndRetailPrices();

    void finalizePrice();

    void setName(String name);

    OrderItem clone();

    void assignFinalPrice();

    Money getPriceBeforeAdjustments(boolean allowSalesPrice);

    void addCandidateItemOffer(CandidateItemOffer candidateItemOffer);

    void removeAllCandidateItemOffers();

    int removeAllAdjustments();

    Map<String, OrderItemAttribute> getOrderItemAttributes();

    void setOrderItemAttributes(Map<String, OrderItemAttribute> orderItemAttributes);

    Money getAveragePrice();

    Money getAverageAdjustmentValue();

    Money getTotalAdjustmentValue();

    Money getTotalPrice();

    Boolean isTaxable();

    void setTaxable(Boolean taxable);

    Money getTotalPriceBeforeAdjustments(boolean allowSalesPrice);

    public boolean isSkuActive();

    public List<OrderItem> getChildOrderItems();

    public void setChildOrderItems(List<OrderItem> childOrderItems);

    public OrderItem getParentOrderItem();

    public void setParentOrderItem(OrderItem parentOrderItem);

    public boolean isAParentOf(OrderItem candidateChild);

}
