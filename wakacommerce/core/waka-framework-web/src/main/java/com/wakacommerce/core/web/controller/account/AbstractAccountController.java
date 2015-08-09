
package com.wakacommerce.core.web.controller.account;

import javax.annotation.Resource;

import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.order.service.OrderService;


/**
 *
 * @ hui
 */
public abstract class AbstractAccountController extends WakaAbstractController {
    
    @Resource(name="blOrderService")
    protected OrderService orderService;

    @Resource(name="blCatalogService")
    protected CatalogService catalogService;
    
}
