
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 * 
 *jfischer
 *
 */
public interface OfferItemCriteria extends QuantityBasedRule, MultiTenantCloneable<OfferItemCriteria> {

    //place abstract method definitions here
    
}
