
package com.wakacommerce.core.search.redirect.dao;

import com.wakacommerce.core.search.redirect.domain.SearchRedirect;


/**
 * Created by ppatel.
 */
public interface SearchRedirectDao {


    public SearchRedirect findSearchRedirectBySearchTerm(String uri);

}
