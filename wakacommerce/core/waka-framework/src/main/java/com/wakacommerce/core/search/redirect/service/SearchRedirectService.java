
package com.wakacommerce.core.search.redirect.service;

import com.wakacommerce.core.search.redirect.domain.SearchRedirect;


/**
 * Created by bpolster.
 */
public interface SearchRedirectService {

    /**
     * Checks the passed in URL to determine if there is a matching SearchRedirect.
     * Returns null if no handler was found.
     * 
     * @param uri
     * @return
     */
    public SearchRedirect findSearchRedirectBySearchTerm(String uri);

}
