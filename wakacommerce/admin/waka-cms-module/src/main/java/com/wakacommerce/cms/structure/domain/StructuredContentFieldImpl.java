package com.wakacommerce.cms.structure.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.extensibility.jpa.copy.ProfileEntity;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.openadmin.audit.AdminAuditImpl;
import com.wakacommerce.openadmin.audit.AdminAuditListener;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC_FLD")
@EntityListeners(value = { AdminAuditListener.class })
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class StructuredContentFieldImpl implements StructuredContentField, ProfileEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "StructuredContentFieldId")
    @GenericGenerator(
        name="StructuredContentFieldId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="StructuredContentFieldImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.structure.domain.StructuredContentFieldImpl")
        }
    )
    @Column(name = "SC_FLD_ID")
    protected Long id;

    @Embedded
    @AdminPresentation(excluded = true)
    protected AdminAuditImpl auditable = new AdminAuditImpl();

    @Column (name = "FLD_KEY")
    protected String fieldKey;

    @Column (name = "VALUE")
    protected String stringValue;

    @Column (name = "LOB_VALUE", length = Integer.MAX_VALUE - 1)
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    protected String lobValue;

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
            return stringValue;
        } else {
            return lobValue;
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
    public AdminAuditImpl getAuditable() {
        return auditable;
    }

    @Override
    public void setAuditable(AdminAuditImpl auditable) {
        this.auditable = auditable;
    }
    
    @Override
    public StructuredContentField clone() {
        StructuredContentField clone = null;

        try {
            clone = (StructuredContentField) Class.forName(this.getClass().getName()).newInstance();
            clone.setFieldKey(getFieldKey());
            clone.setValue(getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clone;
    }

    @Override
    public <G extends StructuredContentField> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        StructuredContentField cloned = createResponse.getClone();
        cloned.setFieldKey(fieldKey);
        cloned.setValue(this.getValue());
        return createResponse;
    }
}
