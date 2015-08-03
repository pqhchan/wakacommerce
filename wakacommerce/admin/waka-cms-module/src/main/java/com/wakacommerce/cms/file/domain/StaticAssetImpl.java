package com.wakacommerce.cms.file.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.cms.field.type.StorageType;
import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.RequiredOverride;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeEntry;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverride;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverrides;
import com.wakacommerce.common.presentation.override.PropertyType;
import com.wakacommerce.openadmin.audit.AdminAuditable;
import com.wakacommerce.openadmin.audit.AdminAuditableListener;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = { AdminAuditableListener.class })
@Table(name = "BLC_STATIC_ASSET")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationMergeOverrides(
    {
        @AdminPresentationMergeOverride(name = "auditable.createdBy.id", mergeEntries = {
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
        }),
        @AdminPresentationMergeOverride(name = "auditable.updatedBy.id", mergeEntries = {
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
        }),
        @AdminPresentationMergeOverride(name = "auditable.createdBy.name", mergeEntries = {
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
	    }),
        
        @AdminPresentationMergeOverride(name = "auditable.updatedBy.name", mergeEntries = {
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
        }),
        @AdminPresentationMergeOverride(name = "auditable.dateCreated", mergeEntries = {
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
        }),
        @AdminPresentationMergeOverride(name = "auditable.dateUpdated", mergeEntries = {
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY, booleanOverrideValue = true),
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.VISIBILITY, overrideValue = "HIDDEN_ALL")
		}),
        @AdminPresentationMergeOverride(name = "sandbox", mergeEntries = {
	            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)
		})
    }
)
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class StaticAssetImpl implements StaticAsset, AdminMainEntity {

    private static final long serialVersionUID = 6990685254640110350L;

    @Id
    @GeneratedValue(generator = "StaticAssetId")
    @GenericGenerator(
        name="StaticAssetId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="StaticAssetImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.cms.file.domain.StaticAssetImpl")
        }
    )
    @Column(name = "STATIC_ASSET_ID")
    protected Long id;

    @Embedded
    @AdminPresentation(excluded = true)
    protected AdminAuditable auditable = new AdminAuditable();

    @Column(name = "NAME", nullable = false)
    @AdminPresentation(friendlyName = "名称",
            order = Presentation.FieldOrder.NAME,
            requiredOverride = RequiredOverride.NOT_REQUIRED,
            gridOrder = Presentation.FieldOrder.NAME,
            prominent = true)
    protected String name;

    @Column(name ="FULL_URL", nullable = false)
    @AdminPresentation(friendlyName = "地址",
            order = Presentation.FieldOrder.URL,
            gridOrder = Presentation.FieldOrder.URL,
            requiredOverride = RequiredOverride.REQUIRED,
            fieldType = SupportedFieldType.ASSET_URL,
            prominent = true)
    @Index(name="ASST_FULL_URL_INDX", columnNames={"FULL_URL"})
    protected String fullUrl;

    @Column(name = "TITLE", nullable = true)
    @AdminPresentation(friendlyName = "标题",
            order = Presentation.FieldOrder.TITLE)
    protected String title;

    @Column(name = "ALT_TEXT", nullable = true)
    @AdminPresentation(friendlyName = "Alt文本",
            order = Presentation.FieldOrder.ALT_TEXT)
    protected String altText;

    @Column(name = "MIME_TYPE")
    @AdminPresentation(friendlyName = "Mime类型",
            order = Presentation.FieldOrder.MIME_TYPE,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected String mimeType;

    @Column(name = "FILE_SIZE")
    @AdminPresentation(friendlyName = "尺寸",
            order = Presentation.FieldOrder.FILE_SIZE,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Long fileSize;

    @Column(name = "FILE_EXTENSION")
    @AdminPresentation(friendlyName = "扩展名",
            order = Presentation.FieldOrder.FILE_EXTENSION,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected String fileExtension;

    @Column(name = "STORAGE_TYPE")
    @AdminPresentation(excluded = true)
    protected String storageType;

    @Override
    public String getFullUrl() {
        return fullUrl;
    }

    @Override
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAltText() {
        return altText;
    }

    @Override
    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public Long getFileSize() {
        return fileSize;
    }

    @Override
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
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
    public StorageType getStorageType() {
        StorageType st = StorageType.getInstance(storageType);
        if (st == null) {
            return StorageType.DATABASE;
        } else {
            return st;
        }
    }

    @Override
    public void setStorageType(StorageType storageType) {
        this.storageType = storageType.getType();
    }

    @Override
    public <G extends StaticAsset> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        StaticAsset cloned = createResponse.getClone();
        cloned.setName(name);
        cloned.setAltText(altText);
        cloned.setFileExtension(fileExtension);
        cloned.setFileSize(fileSize);
        cloned.setFullUrl(fullUrl);
        cloned.setMimeType(mimeType);
        cloned.setTitle(title);
        cloned.setStorageType(getStorageType());

        return createResponse;
    }

    public static class Presentation {

        public static class Tab {

            public static class Name {

                public static final String File_Details = "详细信息";
                public static final String Advanced = "高级";
            }

            public static class Order {

                public static final int File_Details = 2000;
                public static final int Advanced = 3000;
            }
        }

        public static class FieldOrder {

            // General Fields
            public static final int NAME = 1000;
            public static final int URL = 2000;
            public static final int TITLE = 3000;
            public static final int ALT_TEXT = 4000;

            public static final int MIME_TYPE = 5000;
            public static final int FILE_EXTENSION = 6000;
            public static final int FILE_SIZE = 7000;
            
            // Used by subclasses to know where the last field is.
            public static final int LAST = 7000;

        }
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }
}
