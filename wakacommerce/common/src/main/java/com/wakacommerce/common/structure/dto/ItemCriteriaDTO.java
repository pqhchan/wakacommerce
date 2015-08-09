
package com.wakacommerce.common.structure.dto;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public class ItemCriteriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Integer qty;
    protected String matchRule;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public ItemCriteriaDTO getClone() {
        ItemCriteriaDTO clonedDto = new ItemCriteriaDTO();
        clonedDto.setQty(qty);
        clonedDto.setMatchRule(matchRule);
        return clonedDto;
    }
}
