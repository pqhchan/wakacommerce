
package com.wakacommerce.core.order.domain;

import org.springframework.stereotype.Service;

/**
 *
 * @ hui
 */
@Service("blNullOrderFactory")
public class NullOrderFactoryImpl implements NullOrderFactory {
    
    protected static final Order NULL_ORDER = new NullOrderImpl();

    @Override
    public Order getNullOrder() {
        return NULL_ORDER;
    }

}
