
package com.wakacommerce.common.web.expression;

/**
 * Classes that implement this interface will be exposed to the Thymeleaf expression evaluation context.
 * If an implementing class defines its name as "theme" and has a method called attr(String name), that method
 * could then be invoked by ${#theme.attr('someName')}.
 * 
 *Andre Azzolini (apazzolini)
 */
public interface BroadleafVariableExpression {
    
    public String getName();
    
}
