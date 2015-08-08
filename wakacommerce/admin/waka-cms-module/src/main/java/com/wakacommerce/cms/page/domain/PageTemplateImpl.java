package com.wakacommerce.cms.page.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.clone.ClonePolicyCollectionOverride;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_TMPLT")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "PageTemplateImpl_basePageTemplate")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class PageTemplateImpl implements PageTemplate, AdminMainEntity, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageTemplateId")
    @GenericGenerator(
        name="PageTemplateId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PageTemplateImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.page.domain.PageTemplateImpl")
        }
    )
    @Column(name = "PAGE_TMPLT_ID")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Id", 
        visibility = VisibilityEnum.HIDDEN_ALL, 
        readOnly = true)
    protected Long id;

    @Column (name = "TMPLT_NAME")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Name", 
        prominent = true, gridOrder = 1)
    protected String templateName;

    @Column (name = "TMPLT_DESCR")
    protected String templateDescription;

    @Column (name = "TMPLT_PATH")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Path", 
        visibility = VisibilityEnum.HIDDEN_ALL, 
        readOnly = true)
    protected String templatePath;

    @OneToMany(targetEntity = PageTemplateFieldGroupXrefImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "pageTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blCMSElements")
    @OrderBy("groupOrder")
    @BatchSize(size = 20)
    @ClonePolicyCollectionOverride
    protected List<PageTemplateFieldGroupXref> fieldGroups = new ArrayList<PageTemplateFieldGroupXref>();

    @Transient
    protected List<FieldGroup> legacyFieldGroups = new ArrayList<FieldGroup>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String getTemplateDescription() {
        return templateDescription;
    }

    @Override
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public List<PageTemplateFieldGroupXref> getFieldGroupXrefs() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroupXrefs(List<PageTemplateFieldGroupXref> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    @Override
    public String getMainEntityName() {
        return getTemplateName();
    }

    @Override
    public <G extends PageTemplate> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        PageTemplate cloned = createResponse.getClone();
        cloned.setTemplateName(templateName);
        cloned.setTemplateDescription(templateDescription);
        cloned.setTemplatePath(templatePath);
        for (PageTemplateFieldGroupXref fieldGroup : fieldGroups) {
            CreateResponse<PageTemplateFieldGroupXref> clonedGroupResponse = fieldGroup.createOrRetrieveCopyInstance(context);
            PageTemplateFieldGroupXref clonedGroup = clonedGroupResponse.getClone();
            cloned.getFieldGroupXrefs().add(clonedGroup);
        }
        return createResponse;
    }

}

