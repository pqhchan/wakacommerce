
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.SimpleRule;

/**
 * 
 *  
 *
 */
public interface OfferRule extends SimpleRule, MultiTenantCloneable<OfferRule> {

    public Long getId();

    public void setId(Long id);

    public String getMatchRule();

    public void setMatchRule(String matchRule);

}
