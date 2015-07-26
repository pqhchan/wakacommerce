
package com.wakacommerce.core.web.layout.tags;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.legacy.LegacyCartService;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class OrderLookupTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    private Long orderId;
    private String orderName;
    private String orderVar;
    private String totalQuantityVar;

    @Override
    public int doStartTag() throws JspException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        Customer customer = CustomerState.getCustomer((HttpServletRequest) pageContext.getRequest());
        LegacyCartService cartService = (LegacyCartService) applicationContext.getBean("blOrderService");
        Order order = null;
        if (orderName != null && orderId != null) {
            throw new IllegalArgumentException("Only orderName or orderId attribute may be specified on orderLookup tag");
        } else if (orderId != null) {
            order = cartService.findOrderById(orderId);
        } else if (orderName != null) {
            order = cartService.findNamedOrderForCustomer(orderName, customer);
        } else if (customer != null){
            order = cartService.findCartForCustomer(customer);
        }
        if (orderVar != null) {
            pageContext.setAttribute(orderVar, order);
        }
        if (totalQuantityVar != null) {
            int orderItemsCount = 0;
            if (order != null && order.getOrderItems() != null) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItemsCount += orderItem.getQuantity();
                }
            }
            pageContext.setAttribute(totalQuantityVar, orderItemsCount);
        } else if (totalQuantityVar != null) {
            pageContext.setAttribute(totalQuantityVar, 0);
        }
        return EVAL_PAGE;
    }

    public String getOrderVar() {
        return orderVar;
    }

    public void setOrderVar(String orderVar) {
        this.orderVar = orderVar;
    }

    public String getTotalQuantityVar() {
        return totalQuantityVar;
    }

    public void setTotalQuantityVar(String totalQuantityVar) {
        this.totalQuantityVar = totalQuantityVar;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
