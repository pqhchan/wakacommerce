
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
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

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
@Table(name = "BLC_OFFER_ITEM_CRITERIA")
@Inheritance(strategy=InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blOffers")
@AdminPresentationClass(friendlyName = "OfferItemCriteriaImpl_baseOfferItemCriteria")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class OfferItemCriteriaImpl implements OfferItemCriteria {
    
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "OfferItemCriteriaId")
    @GenericGenerator(
        name="OfferItemCriteriaId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OfferItemCriteriaImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.offer.domain.OfferItemCriteriaImpl")
        }
    )
    @Column(name = "OFFER_ITEM_CRITERIA_ID")
    @AdminPresentation(friendlyName = "OfferItemCriteriaImpl_Item_Criteria_Id", group = "OfferItemCriteriaImpl_Description", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;
    
    @Column(name = "QUANTITY", nullable=false)
    @AdminPresentation(friendlyName = "OfferItemCriteriaImpl_Quantity", group = "OfferItemCriteriaImpl_Description", visibility =VisibilityEnum.HIDDEN_ALL)
    protected Integer quantity;
    
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "ORDER_ITEM_MATCH_RULE", length = Integer.MAX_VALUE - 1)
    @AdminPresentation(friendlyName = "OfferItemCriteriaImpl_Order_Item_Match_Rule", group = "OfferItemCriteriaImpl_Description", visibility = VisibilityEnum.HIDDEN_ALL)
    protected String orderItemMatchRule;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer receiveQuantity) {
        this.quantity = receiveQuantity;
    }

    @Override
    public String getMatchRule() {
        return orderItemMatchRule;
    }

    @Override
    public void setMatchRule(String matchRule) {
        this.orderItemMatchRule = matchRule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((orderItemMatchRule == null) ? 0 : orderItemMatchRule.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
        OfferItemCriteriaImpl other = (OfferItemCriteriaImpl) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        if (orderItemMatchRule == null) {
            if (other.orderItemMatchRule != null)
                return false;
        } else if (!orderItemMatchRule.equals(other.orderItemMatchRule))
            return false;
        if (quantity == null) {
            if (other.quantity != null)
                return false;
        } else if (!quantity.equals(other.quantity))
            return false;
        return true;
    }

    @Override
    public <G extends OfferItemCriteria> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OfferItemCriteria cloned = createResponse.getClone();
        cloned.setQuantity(quantity);
        cloned.setMatchRule(orderItemMatchRule);
       return createResponse;
    }
}
