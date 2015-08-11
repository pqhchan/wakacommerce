package com.wakacommerce.core.web.order.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderLockManager;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.core.web.order.security.exception.OrderLockAcquisitionFailureException;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Component("blCartStateFilter")
public class CartStateFilter extends OncePerRequestFilter implements Ordered {

    protected static final Log LOG = LogFactory.getLog(CartStateFilter.class);

    @Resource(name = "blCartStateRequestProcessor")
    protected CartStateRequestProcessor cartStateProcessor;

    @Resource(name = "blOrderLockManager")
    protected OrderLockManager orderLockManager;
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    protected List<String> excludedOrderLockRequestPatterns;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {        
        cartStateProcessor.process(new ServletWebRequest(request, response));
        
        if (!requestRequiresLock(request)) {
            chain.doFilter(request, response);
            return;
        }

        Order order = CartState.getCart();

        if (LOG.isTraceEnabled()) {
            LOG.trace("Thread[" + Thread.currentThread().getId() + "] attempting to lock order[" + order.getId() + "]");
        }

        Object lockObject = null;
        try {
            if (lockObject == null) {
                if (getErrorInsteadOfQueue()) {
                    lockObject = orderLockManager.acquireLockIfAvailable(order);
                    if (lockObject == null) {
                        // We weren't able to acquire the lock immediately because some other thread has it. Because the
                        // order.lock.errorInsteadOfQueue property was set to true, we're going to throw an exception now.
                        throw new OrderLockAcquisitionFailureException("Thread[" + Thread.currentThread().getId() +
                                "] could not acquire lock for order[" + order.getId() + "]");
                    }
                } else {
                    lockObject = orderLockManager.acquireLock(order);
                }
            }
    
            if (LOG.isTraceEnabled()) {
                LOG.trace("Thread[" + Thread.currentThread().getId() + "] grabbed lock for order[" + order.getId() + "]");
            }

            // When we have a hold of the lock for the order, we want to reload the order from the database.
            // This is because a different thread could have modified the order in between the time we initially
            // read it for this thread and now, resulting in the order being stale. Additionally, we want to make
            // sure we detach the order from the EntityManager and forcefully reload the order.
            CartState.setCart(orderService.reloadOrder(order));

            chain.doFilter(request, response);
        } finally {
            if (lockObject != null) {
                orderLockManager.releaseLock(lockObject);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Thread[" + Thread.currentThread().getId() + "] released lock for order[" + order.getId() +"]");
            }
        }
    }

    protected boolean requestRequiresLock(ServletRequest req) {
        if (!(req instanceof HttpServletRequest)) {
               return false;
        }
        
        if (!orderLockManager.isActive()) {
            return false;
        }

        HttpServletRequest request = (HttpServletRequest) req;

        if (!request.getMethod().equalsIgnoreCase("post")) {
            return false;
        }
        
        if (excludedOrderLockRequestPatterns != null && excludedOrderLockRequestPatterns.size() > 0) {
            for (String pattern : excludedOrderLockRequestPatterns) {
                RequestMatcher matcher = new AntPathRequestMatcher(pattern);
                if (matcher.matches(request)){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int getOrder() {
        //FilterChainOrder has been dropped from Spring Security 3
        //return FilterChainOrder.REMEMBER_ME_FILTER+1;
        return 1502;
    }

    public List<String> getExcludedOrderLockRequestPatterns() {
        return excludedOrderLockRequestPatterns;
    }

    /**
     * This allows you to declaratively set a list of excluded Request Patterns
     *
     * <bean id="blCartStateFilter" class="com.wakacommerce.core.web.order.security.CartStateFilter">
     *     <property name="excludedOrderLockRequestPatterns">
     *         <list>
     *             <value>/exclude-me/**</value>
     *         </list>
     *     </property>
     * </bean>
     *
     **/
    public void setExcludedOrderLockRequestPatterns(List<String> excludedOrderLockRequestPatterns) {
        this.excludedOrderLockRequestPatterns = excludedOrderLockRequestPatterns;
    }

    protected boolean getErrorInsteadOfQueue() {
        return BLCSystemProperty.resolveBooleanSystemProperty("order.lock.errorInsteadOfQueue");
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
