
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.TaxDetail;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "taxDetail")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class TaxDetailWrapper extends BaseWrapper implements APIWrapper<TaxDetail> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected BroadleafEnumerationTypeWrapper taxType;

    @XmlElement
    protected Money amount;

    @XmlElement
    protected BigDecimal rate;

    @XmlElement
    protected String currency;

    @XmlElement
    protected String jurisdictionName;

    @XmlElement
    protected String taxName;

    @XmlElement
    protected String region;

    @XmlElement
    protected String country;

    @Override
    public void wrapDetails(TaxDetail model, HttpServletRequest request) {
        this.id = model.getId();
        if (model.getType() != null) {
            this.taxType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class.getName());
            this.taxType.wrapDetails(model.getType(), request);
        }
        this.amount = model.getAmount();
        this.rate = model.getRate();
        if (model.getCurrency() != null) {
            this.currency = model.getCurrency().getCurrencyCode();
        }
        this.jurisdictionName = model.getJurisdictionName();
        this.taxName = model.getTaxName();
        this.region = model.getRegion();
        this.country = model.getCountry();
    }

    @Override
    public void wrapSummary(TaxDetail model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BroadleafEnumerationTypeWrapper getTaxType() {
        return taxType;
    }

    public void setTaxType(BroadleafEnumerationTypeWrapper taxType) {
        this.taxType = taxType;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
