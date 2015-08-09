
package com.wakacommerce.core.search.redirect.dao;

import com.wakacommerce.core.search.redirect.domain.SearchRedirect;


/**
 *
 * @ hui
 */
public interface SearchRedirectDao {


    public SearchRedirect findSearchRedirectBySearchTerm(String uri);

}
