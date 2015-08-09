
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Adjustment;
import com.wakacommerce.core.offer.domain.Offer;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "adjustment")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class AdjustmentWrapper extends BaseWrapper implements APIWrapper<Adjustment> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long offerid;
    
    @XmlElement
    protected String reason;    

    @XmlElement
    protected String marketingMessage;

    @XmlElement
    protected Money adjustmentValue;

    @XmlElement
    protected String discountType;

    @XmlElement
    protected BigDecimal discountAmount;
    

    public void wrapDetails(Adjustment model, HttpServletRequest request) {
        if (model == null) {
            return;
        }
        this.id = model.getId();
        this.reason = model.getReason();

        Offer offer = model.getOffer();
        if (offer != null) {
            if (model.getReason() == null) {
                this.reason = "OFFER";
            }
            this.offerid = offer.getId();
            this.marketingMessage = offer.getMarketingMessage();
            this.discountType = offer.getDiscountType().getType();
            this.discountAmount = offer.getValue();
        }

        this.adjustmentValue = model.getValue();
    }

    @Override
    public void wrapSummary(Adjustment model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfferid() {
        return offerid;
    }

    public void setOfferid(Long offerid) {
        this.offerid = offerid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMarketingMessage() {
        return marketingMessage;
    }

    public void setMarketingMessage(String marketingMessage) {
        this.marketingMessage = marketingMessage;
    }

    public Money getAdjustmentValue() {
        return adjustmentValue;
    }

    public void setAdjustmentValue(Money adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
