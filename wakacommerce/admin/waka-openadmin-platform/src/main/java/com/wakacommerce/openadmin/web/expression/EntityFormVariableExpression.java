
package com.wakacommerce.openadmin.web.expression;

import com.wakacommerce.common.web.expression.BroadleafVariableExpression;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.Tab;

/**
 *
 * @ hui
 */
public class EntityFormVariableExpression implements BroadleafVariableExpression {
    
    @Override
    public String getName() {
        return "ef";
    }
    
    public boolean isTabActive(EntityForm ef, Tab tab) {
        boolean foundVisibleTab = false;

        for (Tab t : ef.getTabs()) {
            if (tab == t && !foundVisibleTab) {
                return true;
            } else if (tab != t && t.getIsVisible()) {
                foundVisibleTab = true;
            }
        }

        return false;
    }

}
