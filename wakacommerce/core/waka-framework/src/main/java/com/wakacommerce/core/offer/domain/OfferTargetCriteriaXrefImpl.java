
package com.wakacommerce.core.offer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import com.wakacommerce.common.extensibility.jpa.clone.ClonePolicy;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.rule.QuantityBasedRule;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_TAR_CRIT_OFFER_XREF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blOffers")
@AdminPresentationClass(excludeFromPolymorphism = false, populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class OfferTargetCriteriaXrefImpl implements OfferTargetCriteriaXref, QuantityBasedRule {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    public OfferTargetCriteriaXrefImpl(Offer offer, OfferItemCriteria offerItemCriteria) {
        this.offer = offer;
        this.offerItemCriteria = offerItemCriteria;
    }

    public OfferTargetCriteriaXrefImpl() {
        //do nothing - default constructor for Hibernate contract
    }

    @Id
    @GeneratedValue(generator= "OfferTarCritId")
    @GenericGenerator(
        name="OfferTarCritId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OfferTargetCriteriaXrefImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.offer.domain.OfferTargetCriteriaXrefImpl")
        }
    )
    @Column(name = "OFFER_TAR_CRIT_ID")
    protected Long id;

    //for the basic collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = OfferImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "OFFER_ID")
    @AdminPresentation(excluded = true)
    protected Offer offer;

    //for the basic collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = OfferItemCriteriaImpl.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "OFFER_ITEM_CRITERIA_ID")
    @ClonePolicy
    protected OfferItemCriteria offerItemCriteria;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public OfferItemCriteria getOfferItemCriteria() {
        return offerItemCriteria;
    }

    @Override
    public void setOfferItemCriteria(OfferItemCriteria offerItemCriteria) {
        this.offerItemCriteria = offerItemCriteria;
    }

    @Override
    public Integer getQuantity() {
        createEntityInstance();
        return offerItemCriteria.getQuantity();
    }

    @Override
    public void setQuantity(Integer quantity) {
        createEntityInstance();
        offerItemCriteria.setQuantity(quantity);
    }

    @Override
    public String getMatchRule() {
        createEntityInstance();
        return offerItemCriteria.getMatchRule();
    }

    @Override
    public void setMatchRule(String matchRule) {
        createEntityInstance();
        offerItemCriteria.setMatchRule(matchRule);
    }

    protected void createEntityInstance() {
        if (offerItemCriteria == null) {
            offerItemCriteria = new OfferItemCriteriaImpl();
        }
    }
}
