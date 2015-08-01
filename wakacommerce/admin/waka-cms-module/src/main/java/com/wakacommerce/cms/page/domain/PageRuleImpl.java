 
package com.wakacommerce.cms.page.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Profile;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;

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
@Table(name = "BLC_PAGE_RULE")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class PageRuleImpl implements PageRule, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "PageRuleId")
    @GenericGenerator(
        name="PageRuleId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PageRuleImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.page.domain.PageRuleImpl")
        }
    )
    @Column(name = "PAGE_RULE_ID")
    protected Long id;
    
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "MATCH_RULE", length = Integer.MAX_VALUE - 1)
    protected String matchRule;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getMatchRule() {
        return matchRule;
    }

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
        PageRuleImpl other = (PageRuleImpl) obj;
        
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
    public PageRule cloneEntity() {
        PageRuleImpl newField = new PageRuleImpl();
        newField.matchRule = matchRule;

        return newField;
    }

    @Override
    public <G extends PageRule> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        PageRule cloned = createResponse.getClone();
        cloned.setMatchRule(matchRule);
        return createResponse;
    }
}
