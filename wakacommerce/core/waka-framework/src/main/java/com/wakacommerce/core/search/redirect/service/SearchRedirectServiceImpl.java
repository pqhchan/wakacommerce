
package com.wakacommerce.core.search.redirect.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.search.redirect.dao.SearchRedirectDao;
import com.wakacommerce.core.search.redirect.domain.SearchRedirect;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blSearchRedirectService")
public class SearchRedirectServiceImpl implements SearchRedirectService {

  
    @Resource(name = "blSearchRedirectDao")
    protected SearchRedirectDao SearchRedirectDao;

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
