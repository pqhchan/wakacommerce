 
package com.wakacommerce.cms.url.dao;

import java.util.List;

import com.wakacommerce.cms.url.domain.URLHandler;


/**
 *
 * @ hui
 */
public interface URLHandlerDao {


    URLHandler findURLHandlerByURI(String uri);

    List<URLHandler> findAllURLHandlers();

    URLHandler saveURLHandler(URLHandler handler);

    URLHandler findURLHandlerById(Long id);

}
