
package com.wakacommerce.core.search.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.presentation.override.AdminPresentationOverride;
import com.wakacommerce.common.presentation.override.AdminPresentationOverrides;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_FACET_RANGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@AdminPresentationOverrides({
        @AdminPresentationOverride(name = "priceList.friendlyName", value = @AdminPresentation(excluded = false, friendlyName = "PriceListImpl_Friendly_Name", order=1, group = "SearchFacetRangeImpl_Description", prominent=true, visibility = VisibilityEnum.FORM_HIDDEN))
})
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class SearchFacetRangeImpl implements SearchFacetRange,Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SearchFacetRangeId")
    @GenericGenerator(
        name="SearchFacetRangeId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SearchFacetRangeImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.search.domain.SearchFacetRangeImpl")
        }
    )
    @Column(name = "SEARCH_FACET_RANGE_ID")
    protected Long id;
    
    @ManyToOne(targetEntity = SearchFacetImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SEARCH_FACET_ID")
    @Index(name="SEARCH_FACET_INDEX", columnNames={"SEARCH_FACET_ID"})
    @AdminPresentation(excluded = true, visibility = VisibilityEnum.HIDDEN_ALL)
    protected SearchFacet searchFacet = new SearchFacetImpl();
    
    @Column(name = "MIN_VALUE", precision=19, scale=5, nullable = false) 
    @AdminPresentation(friendlyName = "SearchFacetRangeImpl_minValue", order=2, group = "SearchFacetRangeImpl_Description", prominent=true)
    protected BigDecimal minValue;
    
    @Column(name = "MAX_VALUE", precision=19, scale=5)
    @AdminPresentation(friendlyName = "SearchFacetRangeImpl_maxValue", order=3, group = "SearchFacetRangeImpl_Description", prominent=true)
    protected BigDecimal maxValue;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    public BigDecimal getMinValue() {
        return minValue;
    }

    @Override
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    @Override
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public <G extends SearchFacetRange> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        SearchFacetRange cloned = createResponse.getClone();
        cloned.setMaxValue(maxValue);
        cloned.setMinValue(minValue);
        if (searchFacet != null) {
            cloned.setSearchFacet(searchFacet.createOrRetrieveCopyInstance(context).getClone());
        }
        return  createResponse;
    }
}
