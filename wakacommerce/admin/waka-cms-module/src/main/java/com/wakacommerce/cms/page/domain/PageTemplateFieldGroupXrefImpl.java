package com.wakacommerce.cms.page.domain;

import java.math.BigDecimal;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.cms.field.domain.FieldGroupImpl;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.clone.ClonePolicy;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PGTMPLT_FLDGRP_XREF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blCMSElements")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class PageTemplateFieldGroupXrefImpl implements PageTemplateFieldGroupXref, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageTemplateFieldGroupXrefId")
    @GenericGenerator(
            name = "PageTemplateFieldGroupXrefId",
            strategy = "com.wakacommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "PageTemplateFieldGroupXrefImpl"),
                    @Parameter(name = "entity_name", value = "com.wakacommerce.cms.page.domain.PageTemplateFieldGroupXrefImpl")
            })
    @Column(name = "PG_TMPLT_FLD_GRP_ID")
    protected Long id;

    @ManyToOne(targetEntity = PageTemplateImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PAGE_TMPLT_ID")
    @AdminPresentation(excluded = true)
    protected PageTemplate pageTemplate;

    @ManyToOne(targetEntity = FieldGroupImpl.class, cascade = { CascadeType.ALL })
    @JoinColumn(name = "FLD_GROUP_ID")
    @ClonePolicy
    protected FieldGroup fieldGroup;

    @Column(name = "GROUP_ORDER", precision = 10, scale = 6)
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected BigDecimal groupOrder;

    public PageTemplateFieldGroupXrefImpl() {
        //Default constructor for JPA
    }

    public PageTemplateFieldGroupXrefImpl(PageTemplate pageTemplate, FieldGroup fieldGroup) {
        this.pageTemplate = pageTemplate;
        this.fieldGroup = fieldGroup;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setPageTemplate(PageTemplate pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    @Override
    public PageTemplate getPageTemplate() {
        return pageTemplate;
    }

    @Override
    public void setFieldGroup(FieldGroup fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    @Override
    public FieldGroup getFieldGroup() {
        return fieldGroup;
    }

    @Override
    public void setGroupOrder(BigDecimal groupOrder) {
        this.groupOrder = groupOrder;
    }

    @Override
    public BigDecimal getGroupOrder() {
        return groupOrder;
    }

    @Override
    public <G extends PageTemplateFieldGroupXref> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        PageTemplateFieldGroupXref cloned = createResponse.getClone();
        if (pageTemplate != null) {
            cloned.setPageTemplate(pageTemplate.createOrRetrieveCopyInstance(context).getClone());
        }
        if (fieldGroup != null) {
            cloned.setFieldGroup(fieldGroup.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setGroupOrder(groupOrder);
        return createResponse;
    }
}
