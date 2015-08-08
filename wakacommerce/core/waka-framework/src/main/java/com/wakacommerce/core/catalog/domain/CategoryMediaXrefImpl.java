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
@Table(name = "BLC_CATEGORY_MEDIA_MAP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blCategories")
@AdminPresentationClass(excludeFromPolymorphism = false, populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class CategoryMediaXrefImpl implements CategoryMediaXref, Media, MultiTenantCloneable<CategoryMediaXrefImpl> {

    private static final long serialVersionUID = 1L;

    public CategoryMediaXrefImpl(Category category, Media media, String key) {
        this.category = category;
        this.media = media;
        this.key = key;
    }

    public CategoryMediaXrefImpl() {
        //support default constructor for Hibernate
    }

    @Id
    @GeneratedValue(generator= "CategoryMediaId")
    @GenericGenerator(
        name="CategoryMediaId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="CategoryMediaXrefImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.catalog.domain.CategoryMediaXrefImpl")
        }
    )
    @Column(name = "CATEGORY_MEDIA_ID")
    protected Long id;

    @ManyToOne(targetEntity = CategoryImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "BLC_CATEGORY_CATEGORY_ID")
    @AdminPresentation(excluded = true)
    protected Category category;

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
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
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
    public <G extends CategoryMediaXrefImpl> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        CategoryMediaXrefImpl cloned = createResponse.getClone();
        if (media != null) {
            cloned.setMedia(((MediaImpl) media).createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setAltText(getAltText());
        cloned.setKey(key);
        if (category != null) {
            cloned.setCategory(category.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setTags(getTags());
        cloned.setUrl(getUrl());
        cloned.setTitle(getTitle());
        return createResponse;
    }
    
}
