
package com.wakacommerce.core.catalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.clone.ClonePolicy;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.common.media.domain.MediaImpl;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.util.UnknownUnwrapTypeException;

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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_MEDIA_MAP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blProducts")
@AdminPresentationClass(excludeFromPolymorphism = false, populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class SkuMediaXrefImpl implements SkuMediaXref, Media, MultiTenantCloneable<SkuMediaXrefImpl> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    public SkuMediaXrefImpl(Sku sku, Media media, String key) {
        this.sku = sku;
        this.media = media;
        this.key = key;
    }

    public SkuMediaXrefImpl() {
        //support default constructor for Hibernate
    }

    @Id
    @GeneratedValue(generator= "SkuMediaId")
    @GenericGenerator(
        name="SkuMediaId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SkuMediaXrefImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.catalog.domain.SkuMediaXrefImpl")
        }
    )
    @Column(name = "SKU_MEDIA_ID")
    protected Long id;

    //for the basic collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = SkuImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "BLC_SKU_SKU_ID")
    @AdminPresentation(excluded = true)
    protected Sku sku;

    //for the basic collection join entity - don't pre-instantiate the reference (i.e. don't do myField = new MyFieldImpl())
    @ManyToOne(targetEntity = MediaImpl.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "MEDIA_ID")
    @ClonePolicy
    protected Media media;

    @Column(name = "MAP_KEY", nullable=false)
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected String key;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Sku getSku() {
        return sku;
    }

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Override
    public Media getMedia() {
        return media;
    }

    @Override
    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getUrl() {
        createEntityInstance();
        return media.getUrl();
    }

    @Override
    public void setUrl(String url) {
        createEntityInstance();
        media.setUrl(url);
    }

    @Override
    public String getTitle() {
        createEntityInstance();
        return media.getTitle();
    }

    @Override
    public void setTitle(String title) {
        createEntityInstance();
        media.setTitle(title);
    }

    @Override
    public String getAltText() {
        createEntityInstance();
        return media.getAltText();
    }

    @Override
    public void setAltText(String altText) {
        createEntityInstance();
        media.setAltText(altText);
    }

    @Override
    public String getTags() {
        createEntityInstance();
        return media.getTags();
    }

    @Override
    public void setTags(String tags) {
        createEntityInstance();
        media.setTags(tags);
    }

    protected void createEntityInstance() {
        if (media == null) {
            media = new MediaImpl();
        }
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return Media.class.equals(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (isUnwrappableAs(unwrapType)) {
            return (T) media;
        }
        throw new UnknownUnwrapTypeException(unwrapType);
    }

    @Override
    public <G extends SkuMediaXrefImpl> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        SkuMediaXrefImpl cloned = createResponse.getClone();
        if (media != null) {
            cloned.setMedia(((MediaImpl) media).createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setAltText(getAltText());
        cloned.setKey(key);
        if (sku != null) {
            cloned.setSku(sku.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setTags(getTags());
        cloned.setUrl(getUrl());
        cloned.setTitle(getTitle());
        return createResponse;
    }


}
