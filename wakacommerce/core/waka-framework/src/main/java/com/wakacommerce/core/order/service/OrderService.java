
package com.wakacommerce.core.order.service;

import org.apache.commons.logging.Log;

import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.exception.OfferException;
import com.wakacommerce.core.offer.service.exception.OfferMaxUseExceededException;
import com.wakacommerce.core.order.dao.OrderDao;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.call.GiftWrapOrderItemRequest;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.exception.AddToCartException;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.order.service.exception.UpdateCartException;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.secure.Referenced;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.workflow.WorkflowException;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;

/**
 *
 * @ hui
 */
public interface OrderService {

    public Order createNewCartForCustomer(Customer customer);

    public Order createNamedOrderForCustomer(String name, Customer customer);

    public Order findNamedOrderForCustomer(String name, Customer customer);

    public Order findOrderById(Long orderId);

    public Order findOrderById(Long orderId, boolean refresh);

    public Order findCartForCustomer(Customer customer);

    public List<Order> findOrdersForCustomer(Customer customer);

    public List<Order> findOrdersForCustomer(Customer customer, OrderStatus status);

    public Order findOrderByOrderNumber(String orderNumber);

    public List<OrderPayment> findPaymentsForOrder(Order order);

    public OrderPayment addPaymentToOrder(Order order, OrderPayment payment, Referenced securePaymentInfo);

    public Order save(Order order, Boolean priceOrder) throws PricingException;

    public Order save(Order order, boolean priceOrder, boolean repriceItems) throws PricingException;

    public void cancelOrder(Order order);

    public Order addOfferCode(Order order, OfferCode offerCode, boolean priceOrder) throws PricingException, OfferException;

    public Order addOfferCodes(Order order, List<OfferCode> offerCodes, boolean priceOrder) throws PricingException, OfferException;

    public Order removeOfferCode(Order order, OfferCode offerCode, boolean priceOrder) throws PricingException;

    public Order removeAllOfferCodes(Order order, boolean priceOrder) throws PricingException;

    public Order getNullOrder();

    public boolean getAutomaticallyMergeLikeItems();

    public void setAutomaticallyMergeLikeItems(boolean automaticallyMergeLikeItems);

    public Order confirmOrder(Order order);

    public OrderItem findLastMatchingItem(Order order, Long skuId, Long productId);

    public OrderItem addGiftWrapItemToOrder(Order order, GiftWrapOrderItemRequest itemRequest, boolean priceOrder) throws PricingException;

    public Order addItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException;

    public Order addItemWithPriceOverrides(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException;

    public Order updateItemQuantity(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws UpdateCartException, RemoveFromCartException;

    public Order removeItem(Long orderId, Long orderItemId, boolean priceOrder) throws RemoveFromCartException;

    public boolean isMoveNamedOrderItems();

    public void setMoveNamedOrderItems(boolean moveNamedOrderItems);

    public boolean isDeleteEmptyNamedOrders();

    public void setDeleteEmptyNamedOrders(boolean deleteEmptyNamedOrders);

    public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, boolean priceOrder) throws RemoveFromCartException, AddToCartException;

    public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, int quantity, boolean priceOrder) throws RemoveFromCartException, AddToCartException, UpdateCartException;

    public Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder) throws RemoveFromCartException, AddToCartException;

    public void removeAllPaymentsFromOrder(Order order);

    public void removePaymentsFromOrder(Order order, PaymentType paymentInfoType);

    public void removePaymentFromOrder(Order order, OrderPayment paymentInfo);

    public void deleteOrder(Order cart);

    Order removeInactiveItems(Long orderId, boolean priceOrder) throws RemoveFromCartException;

    Order updateProductOptionsForItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws UpdateCartException;

    public void printOrder(Order order, Log log);

    public void preValidateCartOperation(Order cart);

    public void preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto);

    public Order reloadOrder(Order order);

    public boolean acquireLock(Order order);

    public boolean releaseLock(Order order);
}
