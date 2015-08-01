
package com.wakacommerce.core.web.controller.account;

import javax.annotation.Resource;

import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.order.service.OrderService;


/**
 * An abstract controller that provides convenience methods and resource declarations for its children. 
 * 
 * Operations that are shared between controllers that deal with the cart belong here.
 * 
 *  
 */
public abstract class AbstractAccountController extends WakaAbstractController {
    
    @Resource(name="blOrderService")
    protected OrderService orderService;

    @Resource(name="blCatalogService")
    protected CatalogService catalogService;
    
}
