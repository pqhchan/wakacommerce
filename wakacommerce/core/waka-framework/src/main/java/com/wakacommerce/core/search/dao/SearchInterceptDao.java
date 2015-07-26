
package com.wakacommerce.core.search.dao;

import java.util.List;

import com.wakacommerce.core.search.domain.SearchIntercept;
import com.wakacommerce.core.search.redirect.dao.SearchRedirectDao;

/**
 * @deprecated Replaced in functionality by {@link SearchRedirectDao}
 */
@Deprecated
public interface SearchInterceptDao {
    public SearchIntercept findInterceptByTerm(String term);
    public List<SearchIntercept> findAllIntercepts();
    public void createIntercept(SearchIntercept intercept);
    public void updateIntercept(SearchIntercept intercept);
    public void deleteIntercept(SearchIntercept intercept);
}
