package com.wakacommerce.common.rule;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface QuantityBasedRule extends Serializable {

    public Integer getQuantity();

    public void setQuantity(Integer quantity);

    public String getMatchRule();

    public void setMatchRule(String matchRule);

    public Long getId();

    public void setId(Long id);

}
