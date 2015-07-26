
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.ui.Model;

import com.wakacommerce.common.web.controller.BroadleafAbstractController;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BroadleafOrderConfirmationController extends BroadleafAbstractController {

    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blConfirmationControllerExtensionManager")
    protected ConfirmationControllerExtensionManager extensionManager;
    
    protected static String orderConfirmationView = "checkout/confirmation";

    public String displayOrderConfirmationByOrderNumber(String orderNumber, Model model,
             HttpServletRequest request, HttpServletResponse response) {
        Customer customer = CustomerState.getCustomer();
        if (customer != null) {
            Order order = orderService.findOrderByOrderNumber(orderNumber);
            if (order != null && customer.equals(order.getCustomer())) {
                extensionManager.getProxy().processAdditionalConfirmationActions(order);

                model.addAttribute("order", order);
                return getOrderConfirmationView();
            }
        }
        return null;
    }

    public String displayOrderConfirmationByOrderId(Long orderId, Model model,
             HttpServletRequest request, HttpServletResponse response) {

        Customer customer = CustomerState.getCustomer();
        if (customer != null) {
            Order order = orderService.findOrderById(orderId);
            if (order != null && customer.equals(order.getCustomer())) {
                extensionManager.getProxy().processAdditionalConfirmationActions(order);

                model.addAttribute("order", order);
                return getOrderConfirmationView();
            }
        }
        return null;
    }

    public String getOrderConfirmationView() {
        return orderConfirmationView;
    }

}
