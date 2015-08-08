
package com.wakacommerce.core.order.service.workflow.add;

import com.wakacommerce.common.dao.GenericEntityDao;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.DiscreteOrderItemRequest;
import com.wakacommerce.core.order.service.call.NonDiscreteOrderItemRequestDTO;
import com.wakacommerce.core.order.service.call.OrderItemRequest;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.call.ProductBundleOrderItemRequest;
import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import javax.annotation.Resource;

public class AddOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Resource(name = "blGenericEntityDao")
    protected GenericEntityDao genericEntityDao;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Order has been verified in a previous activity -- the values in the request can be trusted
        Order order = request.getOrder();
        
        Sku sku = null;
        if (orderItemRequestDTO.getSkuId() != null) {
            sku = catalogService.findSkuById(orderItemRequestDTO.getSkuId());
        }
        
        Product product = null;
        if (orderItemRequestDTO.getProductId() != null) {
            product = catalogService.findProductById(orderItemRequestDTO.getProductId());
        }
        
        Category category = null;
        if (orderItemRequestDTO.getCategoryId() != null) {
            category = catalogService.findCategoryById(orderItemRequestDTO.getCategoryId());
        } 

        if (category == null && product != null) {
            category = product.getCategory();
        }

        OrderItem item;
        if (orderItemRequestDTO instanceof NonDiscreteOrderItemRequestDTO) {
            NonDiscreteOrderItemRequestDTO ndr = (NonDiscreteOrderItemRequestDTO) orderItemRequestDTO;
            OrderItemRequest itemRequest = new OrderItemRequest();
            itemRequest.setQuantity(ndr.getQuantity());
            itemRequest.setRetailPriceOverride(ndr.getOverrideRetailPrice());
            itemRequest.setSalePriceOverride(ndr.getOverrideSalePrice());
            itemRequest.setItemName(ndr.getItemName());
            itemRequest.setOrder(order);
            item = orderItemService.createOrderItem(itemRequest);
        } else if (product == null || !(product instanceof ProductBundle)) {
            DiscreteOrderItemRequest itemRequest = new DiscreteOrderItemRequest();
            itemRequest.setCategory(category);
            itemRequest.setProduct(product);
            itemRequest.setSku(sku);
            itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            itemRequest.setOrder(order);
            itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
            item = orderItemService.createDiscreteOrderItem(itemRequest);
        } else {
            ProductBundleOrderItemRequest bundleItemRequest = new ProductBundleOrderItemRequest();
            bundleItemRequest.setCategory(category);
            bundleItemRequest.setProductBundle((ProductBundle) product);
            bundleItemRequest.setSku(sku);
            bundleItemRequest.setQuantity(orderItemRequestDTO.getQuantity());
            bundleItemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            bundleItemRequest.setName(product.getName());
            bundleItemRequest.setOrder(order);
            bundleItemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
            bundleItemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());
            item = orderItemService.createBundleOrderItem(bundleItemRequest, false);
        }

        if (orderItemRequestDTO.getParentOrderItemId() != null) {
            OrderItem parent = orderItemService.readOrderItemById(orderItemRequestDTO.getParentOrderItemId());
            item.setParentOrderItem(parent);
        }
        
        order.getOrderItems().add(item);
        request.setOrderItem(item);

        if (!request.isPriceOrder()) {
            //persist the newly created order if we're not going through the pricing flow. This helps with proper
            //fulfillment group association
            genericEntityDao.persist(item);
        }

        return context;
    }

}
