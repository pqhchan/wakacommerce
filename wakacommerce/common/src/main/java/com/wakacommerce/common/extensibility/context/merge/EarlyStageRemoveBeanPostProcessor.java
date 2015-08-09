
package com.wakacommerce.common.extensibility.context.merge;

import org.springframework.core.PriorityOrdered;

/**
 *
 * @ hui
 */
public class EarlyStageRemoveBeanPostProcessor extends AbstractRemoveBeanPostProcessor implements PriorityOrdered {

    protected int order = Integer.MIN_VALUE;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
