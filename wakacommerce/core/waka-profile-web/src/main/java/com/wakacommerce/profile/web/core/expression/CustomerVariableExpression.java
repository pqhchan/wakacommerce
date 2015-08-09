
package com.wakacommerce.profile.web.core.expression;

import com.wakacommerce.common.web.expression.BroadleafVariableExpression;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;


/**
 *
 * @ hui
 */
public class CustomerVariableExpression implements BroadleafVariableExpression {
    
    @Override
    public String getName() {
        return "customer";
    }
    
    public Customer getCurrent() {
        return CustomerState.getCustomer();
    }
    
}
