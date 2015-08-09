
package com.wakacommerce.core.web.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class ProductOptionDisplayProcessor extends AbstractLocalVariableDefinitionElementProcessor {

    public ProductOptionDisplayProcessor() {
        super("product_option_display");
    }

    @Override
    public int getPrecedence() {
        return 100;
    }

    protected void initServices(Arguments arguments) {

    }

    @Override
    protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
        initServices(arguments);
        HashMap<String, String> productOptionDisplayValues = new HashMap<String, String>();
        Map<String, Object> newVars = new HashMap<String, Object>();
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("orderItem"));
        Object item = expression.execute(arguments.getConfiguration(), arguments);
        if (item instanceof DiscreteOrderItem) {
            DiscreteOrderItem orderItem = (DiscreteOrderItem) item;

            for (String i : orderItem.getOrderItemAttributes().keySet()) {
                for (ProductOption option : orderItem.getProduct().getProductOptions()) {
                    if (option.getAttributeName().equals(i) && !StringUtils.isEmpty(orderItem.getOrderItemAttributes().get(i).toString())) {
                        productOptionDisplayValues.put(option.getLabel(), orderItem.getOrderItemAttributes().get(i).toString());
                    }
                }
            }
        }
        newVars.put("productOptionDisplayValues", productOptionDisplayValues);

        return newVars;
    }

    @Override
    protected boolean removeHostElement(Arguments arguments, Element element) {
        return false;
    }
}
