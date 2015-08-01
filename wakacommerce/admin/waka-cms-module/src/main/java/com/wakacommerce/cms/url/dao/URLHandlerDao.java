 
package com.wakacommerce.cms.url.dao;

import java.util.List;

import com.wakacommerce.cms.url.domain.URLHandler;


/**
 * Created by ppatel.
 */
public interface URLHandlerDao {


    URLHandler findURLHandlerByURI(String uri);
    
    /**
     * Gets all the URL handlers configured in the system
     * @return
     */
    List<URLHandler> findAllURLHandlers();

    URLHandler saveURLHandler(URLHandler handler);

    URLHandler findURLHandlerById(Long id);

}
