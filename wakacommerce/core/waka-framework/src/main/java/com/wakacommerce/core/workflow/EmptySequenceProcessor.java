
package com.wakacommerce.core.workflow;

import com.wakacommerce.core.order.service.OrderService;


/**
 * Convenience class for creating an empty workflow. Useful when a user wants to remove workflow behavior from Broadleaf.
 * For instance, a user might want to subclass {@link OrderService} and provide their own implementation of addItem, but
 * wants to invoke the super implementation of this method to obtain all functionality <i>except</i> executing the workflow
 * since they want to take charge of the entire process themselves.
 * 
 *     
 */
public class EmptySequenceProcessor extends SequenceProcessor {

    @Override
    protected ProcessContext createContext(Object seedData) {
        return null;
    }

}
