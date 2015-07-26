
package com.wakacommerce.core.offer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.clone.ClonePolicy;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.rule.SimpleRule;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_OFFER_RULE_MAP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blOffers")
@AdminPresentationClass(excludeFromPolymorphism = false, populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class OfferOfferRuleXrefImpl implements OfferOfferRuleXref, SimpleRule {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    public OfferOfferRuleXrefImpl(Offer offer, OfferRule offerRule, String key) {
        this.offer = offer;
        this.offerRule = offerRule;
        this.key = key;
    }

    public OfferOfferRuleXrefImpl() {
        //support default constructor for Hibernate
    }

    @Id
    @GeneratedValue(generator= "OfferOfferRuleId")
    @GenericGenerator(
        name="OfferOfferRuleId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OfferOfferRuleXrefImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.offer.domain.OfferOfferRuleXrefImpl")
        }
    )
    @Column(name = "OFFER_OFFER_RULE_ID")
    protected Long id;

    //for the collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = OfferImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "BLC_OFFER_OFFER_ID")
    @AdminPresentation(excluded = true)
    protected Offer offer;

    //for the collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = OfferRuleImpl.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "OFFER_RULE_ID")
    @ClonePolicy
    protected OfferRule offerRule;

    @Column(name = "MAP_KEY", nullable=false)
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected String key;

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
    public OfferRule getOfferRule() {
        return offerRule;
    }

    @Override
    public void setOfferRule(OfferRule offerRule) {
        this.offerRule = offerRule;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Nonnull
    @Override
    public String getMatchRule() {
        createEntityInstance();
        return offerRule.getMatchRule();
    }

    @Override
    public void setMatchRule(@Nonnull String matchRule) {
        createEntityInstance();
        offerRule.setMatchRule(matchRule);
    }

    protected void createEntityInstance() {
        if (offerRule == null) {
            offerRule = new OfferRuleImpl();
        }
    }

    @Override
    public <G extends OfferOfferRuleXref> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OfferOfferRuleXref cloned = createResponse.getClone();
        cloned.setKey(key);
        if (offer != null) {
            cloned.setOffer(offer.createOrRetrieveCopyInstance(context).getClone());
        }
        if (offerRule != null) {
            cloned.setOfferRule(offerRule.createOrRetrieveCopyInstance(context).getClone());
        }
        return  createResponse;
    }
}
