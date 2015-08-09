
package com.wakacommerce.core.search.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.core.search.redirect.domain.SearchRedirectImpl;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 *
 * @ hui
 */
@Deprecated
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
public class SearchInterceptImpl implements SearchIntercept {
    
    @Id
    @GeneratedValue(generator = "SearchInterceptId")
    @GenericGenerator(
        name="SearchInterceptId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SearchInterceptImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.search.domain.SearchInterceptImpl")
        }
    )
    @Column(name = "SEARCH_INTERCEPT_ID")
    protected Long id;
    
    @Column(name = "TERM")
    @Index(name="SEARCHINTERCEPT_TERM_INDEX", columnNames={"TERM"})
    private String term;
    
    @Column(name = "REDIRECT")
    private String redirect;

    /* (non-Javadoc)
     * @see com.wakacommerce.core.search.domain.SearchIntercept#getTerm()
     */
    @Override
    public String getTerm() {
        return term;
    }
    /* (non-Javadoc)
     * @see com.wakacommerce.core.search.domain.SearchIntercept#setTerm(java.lang.String)
     */
    @Override
    public void setTerm(String term) {
        this.term = term;
    }
    /* (non-Javadoc)
     * @see com.wakacommerce.core.search.domain.SearchIntercept#getRedirect()
     */
    @Override
    public String getRedirect() {
        return redirect;
    }
    /* (non-Javadoc)
     * @see com.wakacommerce.core.search.domain.SearchIntercept#setRedirect(java.lang.String)
     */
    @Override
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
