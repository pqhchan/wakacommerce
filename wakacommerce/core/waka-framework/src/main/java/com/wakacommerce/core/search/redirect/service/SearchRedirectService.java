
package com.wakacommerce.core.search.redirect.service;

import com.wakacommerce.core.search.redirect.domain.SearchRedirect;


/**
 *
 * @ hui
 */
public interface SearchRedirectService {

    public SearchRedirect findSearchRedirectBySearchTerm(String uri);

}
