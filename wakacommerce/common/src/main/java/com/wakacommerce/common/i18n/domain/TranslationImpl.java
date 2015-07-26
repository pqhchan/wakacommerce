

package com.wakacommerce.common.i18n.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Table;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@javax.persistence.Table(name = "BLC_TRANSLATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blTranslationElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "TranslationImpl_baseTranslation")
//multi-column indexes don't appear to get exported correctly when declared at the field level, so declaring here as a workaround
@Table(appliesTo = "BLC_TRANSLATION", indexes = {
        @Index(name = "TRANSLATION_INDEX", columnNames = {"ENTITY_TYPE","ENTITY_ID","FIELD_NAME","LOCALE_CODE"})
})
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class TranslationImpl implements Serializable, Translation {

    private static final long serialVersionUID = -122818474469147685L;

    @Id
    @GeneratedValue(generator = "TranslationId")
    @GenericGenerator(
        name = "TranslationId",
        strategy = "com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
                @Parameter(name = "segment_value", value = "TranslationImpl"),
                @Parameter(name = "entity_name", value = "com.wakacommerce.common.i18n.domain.TranslationImpl")
        }
    )
    @Column(name = "TRANSLATION_ID")
    protected Long id;

    @Column(name = "ENTITY_TYPE")
    @AdminPresentation(friendlyName = "TranslationImpl_EntityType", prominent = true)
    protected String entityType;

    @Column(name = "ENTITY_ID")
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected String entityId;

    @Column(name = "FIELD_NAME")
    @AdminPresentation(friendlyName = "TranslationImpl_FieldName", prominent = true)
    protected String fieldName;

    @Column(name = "LOCALE_CODE")
    @AdminPresentation(friendlyName = "TranslationImpl_LocaleCode", prominent = true)
    protected String localeCode;

    @Column(name = "TRANSLATED_VALUE", length = Integer.MAX_VALUE - 1)
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @AdminPresentation(friendlyName = "TranslationImpl_TranslatedValue", prominent = true)
    protected String translatedValue;

    /* ************************ */
    /* CUSTOM GETTERS / SETTERS */
    /* ************************ */

    @Override
    public TranslatedEntity getEntityType() {
        return TranslatedEntity.getInstanceFromFriendlyType(entityType);
    }

    @Override
    public void setEntityType(TranslatedEntity entityType) {
        this.entityType = entityType.getFriendlyType();
    }

    /* ************************** */
    /* STANDARD GETTERS / SETTERS */
    /* ************************** */
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getLocaleCode() {
        return localeCode;
    }

    @Override
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    @Override
    public String getTranslatedValue() {
        return translatedValue;
    }

    @Override
    public void setTranslatedValue(String translatedValue) {
        this.translatedValue = translatedValue;
    }

    @Override
    public <G extends Translation> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        Translation cloned = createResponse.getClone();
        cloned.setEntityId(entityId);
        cloned.setFieldName(fieldName);
        cloned.setLocaleCode(localeCode);
        cloned.setTranslatedValue(translatedValue);
        cloned.setEntityType(getEntityType());
        return createResponse;
    }
}
