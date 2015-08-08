package com.wakacommerce.common.extensibility.context.merge;

import org.springframework.core.Ordered;

/**
 * @see EarlyStageMergeBeanPostProcessor
 * @see AbstractMergeBeanPostProcessor
 */
public class LateStageMergeBeanPostProcessor extends AbstractMergeBeanPostProcessor implements Ordered {

    protected int order = Integer.MAX_VALUE;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
