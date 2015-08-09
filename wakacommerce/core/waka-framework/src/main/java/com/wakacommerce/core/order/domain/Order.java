
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.audit.Auditable;
import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.offer.domain.CandidateOrderOffer;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.domain.OfferInfo;
import com.wakacommerce.core.offer.domain.OrderAdjustment;
import com.wakacommerce.core.order.service.call.ActivityMessageDTO;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.pricing.service.workflow.FulfillmentGroupPricingActivity;
import com.wakacommerce.core.pricing.service.workflow.TotalActivity;
import com.wakacommerce.profile.core.domain.Customer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface Order extends Serializable, MultiTenantCloneable<Order> {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Auditable getAuditable();

    void setAuditable(Auditable auditable);

    Money getSubTotal();

    void setSubTotal(Money subTotal);

    void assignOrderItemsFinalPrice();

    Money calculateSubTotal();

    Money getTotal();

    Money getTotalAfterAppliedPayments();

    void setTotal(Money orderTotal);

    Customer getCustomer();

    void setCustomer(Customer customer);

    OrderStatus getStatus();

    void setStatus(OrderStatus status);

    List<OrderItem> getOrderItems();

    void setOrderItems(List<OrderItem> orderItems);

    void addOrderItem(OrderItem orderItem);

    List<FulfillmentGroup> getFulfillmentGroups();

    void setFulfillmentGroups(List<FulfillmentGroup> fulfillmentGroups);

    void setCandidateOrderOffers(List<CandidateOrderOffer> candidateOrderOffers);

    List<CandidateOrderOffer> getCandidateOrderOffers();

    Date getSubmitDate();

    void setSubmitDate(Date submitDate);

    Money getTotalTax();

    void setTotalTax(Money totalTax);

    Money getTotalShipping();

    @Deprecated
    void setTotalShipping(Money totalShipping);

    Money getTotalFulfillmentCharges();

    void setTotalFulfillmentCharges(Money totalFulfillmentCharges);

    List<OrderPayment> getPayments();

    void setPayments(List<OrderPayment> payments);

    boolean hasCategoryItem(String categoryName);

    List<OrderAdjustment> getOrderAdjustments();

    List<DiscreteOrderItem> getDiscreteOrderItems();

    boolean containsSku(Sku sku);

    List<OfferCode> getAddedOfferCodes();

    String getFulfillmentStatus();

    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    Map<Offer, OfferInfo> getAdditionalOfferInformation();

    void setAdditionalOfferInformation(Map<Offer, OfferInfo> additionalOfferInformation);

    Money getItemAdjustmentsValue();

    Money getOrderAdjustmentsValue();

    Money getTotalAdjustmentsValue();

    boolean updatePrices();

    boolean finalizeItemPrices();

    Money getFulfillmentGroupAdjustmentsValue();
    
    void addOfferCode(OfferCode addedOfferCode);
    
    @Deprecated
    void addAddedOfferCode(OfferCode offerCode);

    Map<String,OrderAttribute> getOrderAttributes();

    void setOrderAttributes(Map<String,OrderAttribute> orderAttributes);

    int getItemCount();

    BroadleafCurrency getCurrency();

    void setCurrency(BroadleafCurrency currency);

    Locale getLocale();

    void setLocale(Locale locale);

    boolean getHasOrderAdjustments();

    /*
     * transient field to hold order messages
     */
    List<ActivityMessageDTO> getOrderMessages();

    /*
     * transient field to hold order messages
     */
    void setOrderMessages(List<ActivityMessageDTO> orderMessages);

    public Boolean getTaxOverride();

    public void setTaxOverride(Boolean taxOverride);

}
