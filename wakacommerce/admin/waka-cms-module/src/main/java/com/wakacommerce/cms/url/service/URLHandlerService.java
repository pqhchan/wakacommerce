 
package com.wakacommerce.cms.url.service;
import java.util.List;

import com.wakacommerce.cms.url.domain.URLHandler;

public interface URLHandlerService {

    URLHandler findURLHandlerByURI(String uri);
    
    List<URLHandler> findAllURLHandlers();
    
    URLHandler saveURLHandler(URLHandler handler);

    URLHandler findURLHandlerById(Long id);

}
