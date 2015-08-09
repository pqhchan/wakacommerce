
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.offer.domain.Offer;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "offer")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OfferWrapper extends BaseWrapper implements APIWrapper<Offer> {

    @XmlElement
    protected Long offerId;
    @XmlElement
    protected String startDate;
    @XmlElement
    protected String endDate;
    @XmlElement
    protected String marketingMessage;
    @XmlElement
    protected String description;
    @XmlElement
    protected String name;
    @XmlElement
    protected Boolean automatic;
    @XmlElement
    protected BroadleafEnumerationTypeWrapper offerType;

    @XmlElement
    protected BroadleafEnumerationTypeWrapper discountType;
    @XmlElement
    protected int maxUses;

    @Override
    public void wrapDetails(Offer model, HttpServletRequest request) {
        wrapSummary(model, request);
        this.startDate = model.getStartDate().toString();
        this.endDate = model.getStartDate().toString();
        this.description = model.getDescription();
        this.maxUses = model.getMaxUses();
    }

    @Override
    public void wrapSummary(Offer model, HttpServletRequest request) {
        this.automatic = model.isAutomaticallyAdded();
        this.offerType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class.getName());
        this.offerType.wrapDetails(model.getType(), request);
        this.discountType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class.getName());
        this.discountType.wrapDetails(model.getDiscountType(), request);
        this.offerId = model.getId();
        this.marketingMessage = model.getMarketingMessage();
        this.name = model.getName();
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMarketingMessage() {
        return marketingMessage;
    }

    public void setMarketingMessage(String marketingMessage) {
        this.marketingMessage = marketingMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }

    public BroadleafEnumerationTypeWrapper getOfferType() {
        return offerType;
    }

    public void setOfferType(BroadleafEnumerationTypeWrapper offerType) {
        this.offerType = offerType;
    }

    public BroadleafEnumerationTypeWrapper getDiscountType() {
        return discountType;
    }

    public void setDiscountType(BroadleafEnumerationTypeWrapper discountType) {
        this.discountType = discountType;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }
}
