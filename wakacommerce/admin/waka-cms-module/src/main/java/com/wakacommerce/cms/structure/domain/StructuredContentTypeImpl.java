package com.wakacommerce.cms.structure.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_TYPE")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "StructuredContentTypeImpl_baseStructuredContentType")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class StructuredContentTypeImpl implements StructuredContentType, AdminMainEntity, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "StructuredContentTypeId")
    @GenericGenerator(
        name="StructuredContentTypeId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="StructuredContentTypeImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.structure.domain.StructuredContentTypeImpl")
        }
    )
    @Column(name = "SC_TYPE_ID")
    protected Long id;

    @Column (name = "NAME")
    @AdminPresentation(friendlyName = "StructuredContentTypeImpl_Name", order = 1, gridOrder = 1, group = "StructuredContentTypeImpl_Details", prominent = true)
    @Index(name="SC_TYPE_NAME_INDEX", columnNames={"NAME"})
    protected String name;

    @Column (name = "DESCRIPTION")
    protected String description;

    @ManyToOne(targetEntity = StructuredContentFieldTemplateImpl.class)
    @JoinColumn(name="SC_FLD_TMPLT_ID")
    protected StructuredContentFieldTemplate structuredContentFieldTemplate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public StructuredContentFieldTemplate getStructuredContentFieldTemplate() {
        return structuredContentFieldTemplate;
    }

    @Override
    public void setStructuredContentFieldTemplate(StructuredContentFieldTemplate scft) {
        this.structuredContentFieldTemplate = scft;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

    @Override
    public <G extends StructuredContentType> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        StructuredContentType cloned = createResponse.getClone();
        cloned.setDescription(description);
        cloned.setName(name);
        if (structuredContentFieldTemplate != null) {
            CreateResponse<StructuredContentFieldTemplate> clonedTemplate = structuredContentFieldTemplate
                    .createOrRetrieveCopyInstance(context);
            cloned.setStructuredContentFieldTemplate(clonedTemplate.getClone());
        }
        return createResponse;
    }
}

