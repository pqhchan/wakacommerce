
package com.wakacommerce.core.web.controller.cart;

import com.wakacommerce.common.web.controller.WakaAbstractController;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.payment.service.OrderToPaymentRequestDTOService;
import com.wakacommerce.core.web.service.UpdateCartService;

import javax.annotation.Resource;

/**
 * An abstract controller that provides convenience methods and resource declarations for its
 * children. Operations that are shared between controllers that deal with the catalog belong here.
 * 
 *  
 */
public abstract class AbstractCartController extends WakaAbstractController {
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blOfferService")
    protected OfferService offerService;

    @Resource(name="blUpdateCartService")
    protected UpdateCartService updateCartService;

    @Resource(name = "blOrderToPaymentRequestDTOService")
    protected OrderToPaymentRequestDTOService dtoTranslationService;

}
