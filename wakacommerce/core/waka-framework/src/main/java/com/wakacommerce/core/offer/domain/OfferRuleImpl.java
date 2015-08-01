
package com.wakacommerce.core.offer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 *  
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_OFFER_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blOffers")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class OfferRuleImpl implements OfferRule {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "OfferRuleId")
    @GenericGenerator(
        name="OfferRuleId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OfferRuleImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.offer.domain.OfferRuleImpl")
        }
    )
    @Column(name = "OFFER_RULE_ID")
    protected Long id;
    
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "MATCH_RULE", length = Integer.MAX_VALUE - 1)
    protected String matchRule;

    /* (non-Javadoc)
     * @see com.wakacommerce.core.offer.domain.OfferRule#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.offer.domain.OfferRule#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.offer.domain.OfferRule#getMatchRule()
     */
    @Override
    public String getMatchRule() {
        return matchRule;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.offer.domain.OfferRule#setMatchRule(java.lang.String)
     */
    @Override
    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((matchRule == null) ? 0 : matchRule.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        OfferRuleImpl other = (OfferRuleImpl) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        if (matchRule == null) {
            if (other.matchRule != null)
                return false;
        } else if (!matchRule.equals(other.matchRule))
            return false;
        return true;
    }

    @Override
    public <G extends OfferRule> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OfferRule cloned = createResponse.getClone();
        cloned.setMatchRule(matchRule);
        return createResponse;
    }
}
