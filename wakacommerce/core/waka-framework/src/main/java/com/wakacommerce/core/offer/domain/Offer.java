
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.core.offer.service.type.OfferDeliveryType;
import com.wakacommerce.core.offer.service.type.OfferDiscountType;
import com.wakacommerce.core.offer.service.type.OfferItemRestrictionRuleType;
import com.wakacommerce.core.offer.service.type.OfferType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Offer extends Status, Serializable,MultiTenantCloneable<Offer> {

    public void setId(Long id);

    public Long getId();

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public OfferType getType();

    public void setType(OfferType offerType);

    public OfferDiscountType getDiscountType();

    public void setDiscountType(OfferDiscountType type);

    public BigDecimal getValue();

    public void setValue(BigDecimal value);

    public int getPriority();

    public void setPriority(Integer priority);

    public Date getStartDate();

    public void setStartDate(Date startDate);

    public Date getEndDate();

    public void setEndDate(Date endDate);

    @Deprecated
    public boolean isStackable();

    @Deprecated
    public void setStackable(boolean stackable);

    public String getTargetSystem();

    public void setTargetSystem(String targetSystem);

    public boolean getApplyDiscountToSalePrice();

    public void setApplyDiscountToSalePrice(boolean applyToSalePrice);

    @Deprecated
    public String getAppliesToOrderRules();

    @Deprecated
    public void setAppliesToOrderRules(String appliesToRules);

    @Deprecated
    public String getAppliesToCustomerRules();

    @Deprecated
    public void setAppliesToCustomerRules(String appliesToCustomerRules);

    @Deprecated
    public boolean isApplyDiscountToMarkedItems();

    @Deprecated
    public void setApplyDiscountToMarkedItems(boolean applyDiscountToMarkedItems);
    
    public OfferItemRestrictionRuleType getOfferItemQualifierRuleType();

    public void setOfferItemQualifierRuleType(OfferItemRestrictionRuleType restrictionRuleType);
    
    public OfferItemRestrictionRuleType getOfferItemTargetRuleType();

    public void setOfferItemTargetRuleType(OfferItemRestrictionRuleType restrictionRuleType);

    public boolean isCombinableWithOtherOffers();

    public void setCombinableWithOtherOffers(boolean combinableWithOtherOffers);

    public boolean isAutomaticallyAdded();

    public void setAutomaticallyAdded(boolean automaticallyAdded);

    @Deprecated
    public OfferDeliveryType getDeliveryType();

    @Deprecated
    public void setDeliveryType(OfferDeliveryType deliveryType);

    public Long getMaxUsesPerCustomer();

    public void setMaxUsesPerCustomer(Long maxUses);

    public boolean isUnlimitedUsePerCustomer();

    public boolean isLimitedUsePerCustomer();

    @Deprecated
    public int getMaxUses() ;

    @Deprecated
    public void setMaxUses(int maxUses) ;

    public int getMaxUsesPerOrder();

    public void setMaxUsesPerOrder(int maxUsesPerOrder);

    public boolean isUnlimitedUsePerOrder();

    public boolean isLimitedUsePerOrder();

    @Deprecated
    public int getUses() ;

    @Deprecated
    public void setUses(int uses) ;

    //    /**
    //     * @deprecated use {@link #getQualifyingItemCriteriaXref()} instead
    //     * @return
    //     */
    //    @Deprecated
    //    public Set<OfferItemCriteria> getQualifyingItemCriteria();
    //
    //    /**
    //     * @deprecated use {@link #setQualifyingItemCriteriaXref(java.util.Set)} instead
    //     * @param qualifyingItemCriteria
    //     */
    //    @Deprecated
    //    public void setQualifyingItemCriteria(Set<OfferItemCriteria> qualifyingItemCriteria);

    Set<OfferQualifyingCriteriaXref> getQualifyingItemCriteriaXref();

    void setQualifyingItemCriteriaXref(Set<OfferQualifyingCriteriaXref> qualifyingItemCriteriaXref);

    //    /**
    //     * @deprecated use {@link #getTargetItemCriteriaXref()} instead
    //     * @return
    //     */
    //    @Deprecated
    //    public Set<OfferItemCriteria> getTargetItemCriteria();
    //
    //    /**
    //     * @deprecated use {@link #setTargetItemCriteriaXref(java.util.Set)} instead
    //     * @param targetItemCriteria
    //     */
    //    @Deprecated
    //    public void setTargetItemCriteria(Set<OfferItemCriteria> targetItemCriteria);

    Set<OfferTargetCriteriaXref> getTargetItemCriteriaXref();

    void setTargetItemCriteriaXref(Set<OfferTargetCriteriaXref> targetItemCriteriaXref);
    
    public Boolean isTotalitarianOffer();

    public void setTotalitarianOffer(Boolean totalitarianOffer);

    Map<String, OfferOfferRuleXref> getOfferMatchRulesXref();

    void setOfferMatchRulesXref(Map<String, OfferOfferRuleXref> offerMatchRulesXref);
    
    public Boolean getTreatAsNewFormat();

    public void setTreatAsNewFormat(Boolean treatAsNewFormat);

    public Money getQualifyingItemSubTotal();
    
    public void setQualifyingItemSubTotal(Money qualifyingItemSubtotal);

    public void setMarketingMessage(String marketingMessage);

    public String getMarketingMessage();

    public List<OfferCode> getOfferCodes();

    public void setOfferCodes(List<OfferCode> offerCodes);

    public Boolean getRequiresRelatedTargetAndQualifiers();

    public void setRequiresRelatedTargetAndQualifiers(Boolean requiresRelatedTargetAndQualifiers);

}
