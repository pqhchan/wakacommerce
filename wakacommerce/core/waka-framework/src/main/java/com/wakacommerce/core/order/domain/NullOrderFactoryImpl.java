
package com.wakacommerce.core.order.domain;

import org.springframework.stereotype.Service;

/**
 * @see com.wakacommerce.core.order.domain.NullOrderFactory
 *apazzolini
 */
@Service("blNullOrderFactory")
public class NullOrderFactoryImpl implements NullOrderFactory {
    
    protected static final Order NULL_ORDER = new NullOrderImpl();

    @Override
    public Order getNullOrder() {
        return NULL_ORDER;
    }

}
