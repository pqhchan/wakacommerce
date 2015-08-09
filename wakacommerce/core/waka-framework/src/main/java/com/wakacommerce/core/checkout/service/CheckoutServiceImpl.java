
package com.wakacommerce.core.checkout.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.checkout.service.exception.CheckoutException;
import com.wakacommerce.core.checkout.service.workflow.CheckoutResponse;
import com.wakacommerce.core.checkout.service.workflow.CheckoutSeed;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.workflow.ActivityMessages;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.Processor;
import com.wakacommerce.core.workflow.WorkflowException;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

@Service("blCheckoutService")
public class CheckoutServiceImpl implements CheckoutService {

    @Resource(name="blCheckoutWorkflow")
    protected Processor checkoutWorkflow;

    @Resource(name="blOrderService")
    protected OrderService orderService;

    protected static ConcurrentMap<Long, Object> lockMap = new ConcurrentHashMap<Long, Object>();

    @Override
    public CheckoutResponse performCheckout(Order order) throws CheckoutException {
        //Immediately fail if another thread is currently attempting to check out the order
        Object lockObject = putLock(order.getId());
        if (lockObject != null) {
            throw new CheckoutException("This order is already in the process of being submitted, unable to checkout order -- id: " + order.getId(), new CheckoutSeed(order, new HashMap<String, Object>()));
        }

        // Immediately fail if this order has already been checked out previously
        if (hasOrderBeenCompleted(order)) {
            throw new CheckoutException("This order has already been submitted or cancelled, unable to checkout order -- id: " + order.getId(), new CheckoutSeed(order, new HashMap<String, Object>()));
        }
        
        CheckoutSeed seed = null;
        try {
            // Do a final save of the order before going through with the checkout workflow
            order = orderService.save(order, false);
            seed = new CheckoutSeed(order, new HashMap<String, Object>());

            ProcessContext<CheckoutSeed> context = (ProcessContext<CheckoutSeed>) checkoutWorkflow.doActivities(seed);

            // We need to pull the order off the seed and save it here in case any activity modified the order.
            order = orderService.save(seed.getOrder(), false);
            order.getOrderMessages().addAll(((ActivityMessages) context).getActivityMessages());
            seed.setOrder(order);

            return seed;
        } catch (PricingException e) {
            throw new CheckoutException("Unable to checkout order -- id: " + order.getId(), e, seed);
        } catch (WorkflowException e) {
            throw new CheckoutException("Unable to checkout order -- id: " + order.getId(), e.getRootCause(), seed);
        } catch (RequiredAttributeNotProvidedException e) {
            throw new CheckoutException("Unable to checkout order -- id: " + order.getId(), e.getCause(), seed);
        } finally {
            // The order has completed processing, remove the order from the processing map
            removeLock(order.getId());
        }
    }

    protected boolean hasOrderBeenCompleted(Order order) {
        return (OrderStatus.SUBMITTED.equals(order.getStatus()) || OrderStatus.CANCELLED.equals(order.getStatus()));
    }

    protected Object putLock(Long orderId) {
        return lockMap.putIfAbsent(orderId, new Object());
    }

    protected void removeLock(Long orderId) {
        lockMap.remove(orderId);
    }
    
}
