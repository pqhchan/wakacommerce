
package com.wakacommerce.core.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.web.WakaRequestContext;

/**
 * A Thymeleaf processor that renders a Money object according to the currently set locale options.
 * For example, when rendering "6.99" in a US locale, the output text would be "$6.99".
 * When viewing in France for example, you might see "6,99 (US)$". Alternatively, if currency conversion
 * was enabled, you may see "5,59 (euro-symbol)"
 * 
 *  
 */
public class PriceTextDisplayProcessor extends AbstractTextChildModifierAttrProcessor {

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     */
    public PriceTextDisplayProcessor() {
        super("price");
    }
    
    @Override
    public int getPrecedence() {
        return 1500;
    }

    @Override
    protected String getText(Arguments arguments, Element element, String attributeName) {
        
        Money price = null;

        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue(attributeName));
        Object result = expression.execute(arguments.getConfiguration(), arguments);
        if (result instanceof Money) {
            price = (Money) result;
        } else if (result instanceof Number) {
            price = new Money(((Number)result).doubleValue());
        }


        if (price == null) {
            return "Not Available";
        }

        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc.getJavaLocale() != null) {
            return BroadleafCurrencyUtils.getNumberFormatFromCache(brc.getJavaLocale(), price.getCurrency()).format(price.getAmount());
        } else {
            // Setup your BLC_CURRENCY and BLC_LOCALE to display a diff default.
            return "$ " + price.getAmount().toString();
        }
    }
}
