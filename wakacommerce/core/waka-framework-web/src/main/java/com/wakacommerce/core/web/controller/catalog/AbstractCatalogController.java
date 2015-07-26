
package com.wakacommerce.core.web.controller.catalog;

import com.wakacommerce.common.web.controller.BroadleafAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;

import javax.annotation.Resource;

/**
 * An abstract controller that provides convenience methods and resource declarations for its
 * children. Operations that are shared between controllers that deal with customer accounts belong here
 * 
 *apazzolini
 */
public abstract class AbstractCatalogController extends BroadleafAbstractController {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    

}
