
package com.wakacommerce.core.search.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.CategoryImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CAT_SEARCH_FACET_EXCL_XREF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blCategories")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class CategoryExcludedSearchFacetImpl implements CategoryExcludedSearchFacet, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CategoryExcludedSearchFacetId")
    @GenericGenerator(
        name="CategoryExcludedSearchFacetId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="CategoryExcludedSearchFacetImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.search.domain.CategoryExcludedSearchFacetImpl")
        }
    )
    @Column(name = "CAT_EXCL_SEARCH_FACET_ID")
    protected Long id;

    @ManyToOne(targetEntity = CategoryImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CATEGORY_ID")
    protected Category category;

    @ManyToOne(targetEntity = SearchFacetImpl.class)
    @JoinColumn(name = "SEARCH_FACET_ID")
    protected SearchFacet searchFacet;
    
    @Column(name = "SEQUENCE")
    @AdminPresentation(friendlyName = "CategorySearchFacetImpl_sequence")
    protected BigDecimal sequence;

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
    public SearchFacet getSearchFacet() {
        return searchFacet;
    }

    @Override
    public void setSearchFacet(SearchFacet searchFacet) {
        this.searchFacet = searchFacet;
    }

    @Override
    public BigDecimal getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(BigDecimal sequence) {
        this.sequence = sequence;
    }

    @Override
    public <G extends CategoryExcludedSearchFacet> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        CategoryExcludedSearchFacet cloned = createResponse.getClone();
        if (searchFacet != null) {
            cloned.setSearchFacet(searchFacet.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setSequence(sequence);
        if (category != null) {
            cloned.setCategory(category.createOrRetrieveCopyInstance(context).getClone());
        }
        return createResponse;
    }
}
