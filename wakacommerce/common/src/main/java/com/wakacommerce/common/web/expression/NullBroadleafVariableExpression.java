
package com.wakacommerce.common.web.expression;

/**
 * A null implementation of {@link BroadleafVariableExpression} 
 * 
 *Andre Azzolini (apazzolini)
 */
public class NullBroadleafVariableExpression implements BroadleafVariableExpression {

    @Override
    public String getName() {
        return null;
    }
    
}
