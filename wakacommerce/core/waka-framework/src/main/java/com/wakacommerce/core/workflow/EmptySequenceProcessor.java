
package com.wakacommerce.core.workflow;

import com.wakacommerce.core.order.service.OrderService;


/**
 *
 * @ hui
 */
public class EmptySequenceProcessor extends SequenceProcessor {

    @Override
    protected ProcessContext createContext(Object seedData) {
        return null;
    }

}
