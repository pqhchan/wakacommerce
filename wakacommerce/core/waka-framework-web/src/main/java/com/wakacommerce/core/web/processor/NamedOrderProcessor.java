
package com.wakacommerce.core.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;

/**
 * <p>
 * A Thymeleaf processor that will add the desired named order to the model
 *
 * <p>
 * Example:
 * 
 * <pre>
 *  &lt;blc:named_order orderVar="wishlist" orderName="wishlist" /&gt;
 *  &lt;span th:text="${wishlist.customer.name}" /&gt; 
 * </pre>
 *
 * @param orderVar the value that the order will be assigned to
 * @param orderName the name of the order, {@link Order#getName()}
 * 
 * @see {@link Order#getName()}
 *  
 */
public class NamedOrderProcessor extends AbstractModelVariableModifierProcessor {
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     *
     * NOTE: thymeleaf normalizes the attribute names by converting all to lower-case
     * we will use the underscore instead of camel case to avoid confusion
     *
     */
    public NamedOrderProcessor() {
        super("named_order");
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        Customer customer = CustomerState.getCustomer();

        String orderVar = element.getAttributeValue("orderVar");
        String orderName = element.getAttributeValue("orderName");

        Order order = orderService.findNamedOrderForCustomer(orderName, customer);
        if (order != null) {
            addToModel(arguments, orderVar, order);
        } else {
            addToModel(arguments, orderVar, new NullOrderImpl());
        }
    }
}
