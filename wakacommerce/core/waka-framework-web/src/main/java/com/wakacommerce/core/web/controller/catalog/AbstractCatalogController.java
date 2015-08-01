
package com.wakacommerce.core.web.controller.catalog;

import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;

import javax.annotation.Resource;

/**
 * An abstract controller that provides convenience methods and resource declarations for its
 * children. Operations that are shared between controllers that deal with customer accounts belong here
 * 
 *  
 */
public abstract class AbstractCatalogController extends WakaAbstractController {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    

}
