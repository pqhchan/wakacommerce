
package com.wakacommerce.core.search.redirect.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.search.redirect.dao.SearchRedirectDao;
import com.wakacommerce.core.search.redirect.domain.SearchRedirect;

import javax.annotation.Resource;

/**
 * Created by ppatel.
 */
@Service("blSearchRedirectService")
public class SearchRedirectServiceImpl implements SearchRedirectService {

  
    @Resource(name = "blSearchRedirectDao")
    protected SearchRedirectDao SearchRedirectDao;


    /**
     * Checks the passed in URL to determine if there is a matching
     * SearchRedirect. Returns null if no handler was found.
     * 
     * @param uri
     * @return
     */
    @Override
    public SearchRedirect findSearchRedirectBySearchTerm(String uri) {

        SearchRedirect SearchRedirect = SearchRedirectDao
                .findSearchRedirectBySearchTerm(uri);
        if (SearchRedirect != null) {
            return SearchRedirect;
        } else {
            return null;
        }

    }

}
