package com.wakacommerce.cms.page.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.openadmin.audit.AdminAuditable;
import com.wakacommerce.openadmin.audit.AdminAuditableListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @ hui
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_FLD")
@EntityListeners(value = { AdminAuditableListener.class })
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE, skipOverlaps=true)
})
public class PageFieldImpl implements PageField, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageFieldId")
    @GenericGenerator(
        name="PageFieldId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PageFieldImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.page.domain.PageFieldImpl")
        }
    )
    @Column(name = "PAGE_FLD_ID")
    protected Long id;

    @Embedded
    @AdminPresentation(excluded = true)
    protected AdminAuditable auditable = new AdminAuditable();

    @Column (name = "FLD_KEY")
    protected String fieldKey;

    @Column (name = "VALUE")
    protected String stringValue;

    @Column(name = "LOB_VALUE", length = Integer.MAX_VALUE-1)
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    protected String lobValue;

    @ManyToOne(targetEntity = PageImpl.class, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PAGE_ID")
    protected Page page;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    @Override
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getValue() {
        if (stringValue != null && stringValue.length() > 0) {
            return DynamicTranslationProvider.getValue(page, "pageTemplate|" + fieldKey, stringValue);
        } else {
            return DynamicTranslationProvider.getValue(page, "pageTemplate|" + fieldKey, lobValue);
        }
    }

    @Override
    public void setValue(String value) {
        if (value != null) {
            if (value.length() <= 256) {
                stringValue = value;
                lobValue = null;
            } else {
                stringValue = null;
                lobValue = value;
            }
        } else {
            lobValue = null;
            stringValue = null;
        }
    }

    @Override
    public AdminAuditable getAuditable() {
        return auditable;
    }

    @Override
    public void setAuditable(AdminAuditable auditable) {
        this.auditable = auditable;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public <G extends PageField> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {

        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        PageField cloned = createResponse.getClone();
        if (page != null) {
            cloned.setPage(page.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setFieldKey(fieldKey);
        //we don't want to engage the dynamic behavior housed in the getter/setter methods for these fields
        ((PageFieldImpl) cloned).stringValue = stringValue;
        ((PageFieldImpl) cloned).lobValue = lobValue;

        return createResponse;


    }
}

