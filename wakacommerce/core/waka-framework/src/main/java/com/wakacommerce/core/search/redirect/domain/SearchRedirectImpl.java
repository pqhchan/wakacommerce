
package com.wakacommerce.core.search.redirect.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.util.DateUtil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *priyeshpatel
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_INTERCEPT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "SearchRedirectImpl_friendyName")
public class SearchRedirectImpl implements SearchRedirect, java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Transient
    private static final Log LOG = LogFactory.getLog(SearchRedirectImpl.class);
    
    @Id
    @GeneratedValue(generator = "SearchRedirectID")
    @GenericGenerator(
        name="SearchRedirectID",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SearchRedirectImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.search.redirect.domain.SearchRedirectImpl")
        }
    )
    @Column(name = "SEARCH_REDIRECT_ID")
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;
    
    @Column(name = "PRIORITY")
    @AdminPresentation(excluded = true)
    protected Integer searchPriority;

    @AdminPresentation(friendlyName = "SearchRedirectImpl_searchTerm", order = 1000, group = "SearchRedirectImpl_description", prominent = true, groupOrder = 1)
    @Column(name = "SEARCH_TERM", nullable = false)
    protected String searchTerm;
    
    @Column(name = "URL", nullable = false)
    @AdminPresentation(friendlyName = "SearchRedirectImpl_url", order = 2000, group = "SearchRedirectImpl_description", prominent = true, groupOrder = 1)
    protected String url;

    /** The active start date. */
    @Column(name = "ACTIVE_START_DATE" )
    @AdminPresentation(friendlyName = "SkuImpl_Sku_Start_Date", order = 3000, group = "SearchRedirectImpl_description", tooltip = "skuStartDateTooltip", groupOrder = 1)
    protected Date activeStartDate;

    /** The active end date. */
    @Column(name = "ACTIVE_END_DATE")
    @Index(name="SEARCH_ACTIVE_INDEX", columnNames={"ACTIVE_START_DATE","ACTIVE_END_DATE"})
    @AdminPresentation(friendlyName = "SkuImpl_Sku_End_Date", order = 4000, group = "SearchRedirectImpl_description", tooltip = "skuEndDateTooltip", groupOrder = 1)
    protected Date activeEndDate;
    
    @Override
    public Date getActiveStartDate() {
        return activeStartDate;
    }

    @Override
    public void setActiveStartDate(Date activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    @Override
    public Date getActiveEndDate() {
        return activeEndDate;
    }

    @Override
    public void setActiveEndDate(Date activeEndDate) {
        this.activeEndDate = activeEndDate;
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
    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public Integer getSearchPriority() {
        return searchPriority;
    }

    @Override
    public void setSearchPriority(Integer searchPriority) {
        this.searchPriority = searchPriority;
    }

    @Override
    public boolean isActive() {
        if (LOG.isDebugEnabled()) {
            if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
                LOG.debug("product, " + id + ", inactive due to date");
            }
        }
        return DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true);
    }

}
