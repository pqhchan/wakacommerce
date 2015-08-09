
package com.wakacommerce.core.web.controller.catalog;

import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public abstract class AbstractCatalogController extends WakaAbstractController {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    

}
